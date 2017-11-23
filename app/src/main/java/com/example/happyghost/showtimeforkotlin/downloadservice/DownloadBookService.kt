package com.example.happyghost.showtimeforkotlin.downloadservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.RxBus.event.DownloadEvent
import com.example.happyghost.showtimeforkotlin.RxBus.event.DownloadMessageEvent
import com.example.happyghost.showtimeforkotlin.RxBus.event.DownloadProgressEvent
import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdate.ChapterReadBean
import com.example.happyghost.showtimeforkotlin.utils.FileUtils
import com.example.happyghost.showtimeforkotlin.utils.NetUtil
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/10.
 * @description
 */
class DownloadBookService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        registerRxBus(DownloadEvent::class.java, Consumer { handleDownloadMessage(it) })
    }

    var downloadQueues: MutableList<DownloadEvent> = ArrayList<DownloadEvent>()
    var isBusy = false

    private fun handleDownloadMessage(queue: DownloadEvent) {
        if (!TextUtils.isEmpty(queue.bookId)) {
            var exists = false
            // 判断当前书籍缓存任务是否存在
            for (i in downloadQueues.indices) {
                if (downloadQueues[i].bookId == queue.bookId) {
                    exists = true
                    break
                }
            }
            if (exists) {
                post(DownloadMessageEvent(queue.bookId, "当前缓存任务已存在", false))
                return
            }

            // 添加到下载队列
            downloadQueues.add(queue)
            post(DownloadMessageEvent(queue.bookId, "成功加入缓存队列", false))
        }
        // 从队列顺序取出第一条下载
        if (downloadQueues.size > 0 && !isBusy) {
            isBusy = true
            downloadBook(downloadQueues[0])
        }
    }
    private fun downloadBook(downloadEvent: DownloadEvent) {
        var list = downloadEvent.list
        var bookId = downloadEvent.bookId
        var start = downloadEvent.start // 起始章节
        var end = downloadEvent.end // 结束章节
        doAsync {
            var failureCount = doBackTask(start, end, list, downloadEvent, bookId)
            uiThread {
                doUiTask(downloadEvent, failureCount, bookId)
            }
        }
    }

    private fun doBackTask(start: Int, end: Int, list: List<BookMixATocBean.MixTocBean.ChaptersBean>, downloadEvent: DownloadEvent, bookId: String): Int {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
        }

        var failureCount = 0
        var i = start
        while (i <= end && i <= list.size) {
            if (canceled) {
                break
            }
            // 网络异常，取消下载
            if (!NetUtil.isNetworkAvailable(AppApplication.instance.getContext())) {
                downloadEvent.isCancel = true
                post(DownloadMessageEvent(bookId, getString(R.string.book_read_download_error), true))
                failureCount = -1
                break
            }
            if (!downloadEvent.isFinish && !downloadEvent.isCancel) {
                // 章节文件不存在,则下载，否则跳过
                if (FileUtils.hasChapterFile(bookId, i) == null) {
                    val chapters = list[i - 1]
                    val url = chapters.link
                    val ret = download(url, bookId, chapters.title, i, list.size)
                    if (ret != 1) {
                        failureCount++
                    }
                } else {
                    post(DownloadProgressEvent(bookId, String.format(
                            getString(R.string.book_read_alreday_download), list[i - 1].title, i, list.size),
                            true))
                }
            }
            i++
        }
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
        }
        return failureCount
    }

    private fun download(url: String?, bookId: String, title: String?, chapter: Int, chapterSize: Int): Int {
        val result = intArrayOf(-1)
        RetrofitService.getChapterBody(url!!)
                .subscribeOn(Schedulers.newThread())
                .subscribe(object :Observer<ChapterReadBean>{
                    override fun onComplete() {
                        result[0] = 1
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: ChapterReadBean) {
                        if (t.chapter != null) {
                            post(DownloadProgressEvent(bookId, String.format(
                                    getString(R.string.book_read_download_progress), title, chapter, chapterSize),
                                    true))
                            FileUtils.saveChapterFile(bookId, chapter, t.chapter!!)
                            result[0] = 1
                        } else {
                            result[0] = 0
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        result[0] = 0
                    }
                })
        while (result[0] == -1) {
            try {
                Thread.sleep(350)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
        return result[0]


    }

//    private var mSubscriptionMap: HashMap<String, CompositeDisposable>? = null

//    private fun addSubscrebe(observable: Disposable, downloadBookService: DownloadBookService) {
//        if (mSubscriptionMap == null) {
//            mSubscriptionMap = HashMap()
//        }
//        val key = downloadBookService.javaClass.name
//        if (mSubscriptionMap!![key] != null) {
//            mSubscriptionMap!![key]?.add(observable)
//        } else {
//            //一次性容器,可以持有多个并提供 添加和移除。
//            val disposables = CompositeDisposable()
//            disposables.add(observable)
//            mSubscriptionMap!!.put(key, disposables)
//        }
//    }
//    fun unSubscribe(o: Any) {
//        if (mSubscriptionMap == null) {
//            return
//        }
//
//        val key = o.javaClass.name
//        if (!mSubscriptionMap!!.containsKey(key)) {
//            return
//        }
//        if (mSubscriptionMap!![key] != null) {
//            mSubscriptionMap!![key]?.dispose()
//        }
//
//        mSubscriptionMap!!.remove(key)
//    }

    private fun doUiTask(downloadEvent: DownloadEvent, failureCount: Int, bookId: String) {
        downloadEvent.isFinish = true
        if (failureCount > -1) {
            // 完成通知
            post(DownloadMessageEvent(bookId,
                    String.format(getString(R.string.book_read_download_complete), failureCount), true))
        }
        // 下载完成，从队列里移除
        downloadQueues.remove(downloadEvent)
        // 释放 空闲状态
        isBusy = false
        if (!canceled) {
            // post一个空事件，通知继续执行下一个任务
            post(DownloadEvent())
        } else {
            downloadQueues.clear()
        }
        canceled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterRxBus()
//        unSubscribe(this@DownloadBookService)
    }

    fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {
        val disposable = mRxBus?.doSubscribe(eventType, action, Consumer<Throwable> {
            throwable -> Log.e("NewsMainPresenter", throwable.toString()) })
        mRxBus?.addSubscription(this, disposable!!)
    }

    fun unregisterRxBus() {
        mRxBus?.unSubscribe(this)
    }

    fun post(progress: DownloadProgressEvent) {
        AppApplication.instance.mRxBus!!.post(progress)
    }

    private fun post(message: DownloadMessageEvent) {
        AppApplication.instance.mRxBus!!.post(message)
    }
    companion object {
        var canceled = false
        val mRxBus = RxBus.intanceBus
        fun post(downloadEvent: DownloadEvent){
            mRxBus?.post(downloadEvent)
        }

        fun cancel() {
            canceled = true
        }
    }
}