package com.example.happyghost.showtimeforkotlin.ui.news.channel

import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.RxBus.event.ChannelEvent
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseLocalPresent
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeDao
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfoDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
class ChannelPresenter(view: IBaseChannelView, newsTypeInfoDao: NewsTypeInfoDao, rxBus: RxBus) : IBaseLocalPresent<NewsTypeInfo> {
    var mNewsDao :NewsTypeInfoDao=newsTypeInfoDao
    var mView : IBaseChannelView = view
    var mRxBus :RxBus = rxBus
    override fun getData() {
        val checkList = mNewsDao.queryBuilder().list()
        val typeList = ArrayList<String>()
        val unList = ArrayList<NewsTypeInfo>()
        val ckList = ArrayList<NewsTypeInfo>()
        for (bean in checkList){
            typeList.add(bean.typeId)
        }
        Observable.fromIterable(NewsTypeDao.getAllChannels())//将数据集合遍历发出
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { return@filter !typeList.contains(it.typeId) }//过滤已选中的栏目
                .toList()
                .subscribe { unCheckList, e ->
                    unList.addAll(unCheckList)
                    ckList.addAll(checkList)
                    mView.loadData(ckList,unList)

                }
    }

    override fun getMoreData() {

    }
    override fun insert(data: NewsTypeInfo) {
        Observable.create<NewsTypeInfo> {
            mNewsDao.insert(data)
            it.onNext(data) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mRxBus.post(ChannelEvent(ChannelEvent.ADD_EVENT,data))
                })

    }

    override fun delete(data: NewsTypeInfo) {
        Observable.create<NewsTypeInfo> {
            mNewsDao.delete(data)
            it.onNext(data)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mRxBus.post(ChannelEvent(ChannelEvent.DEL_EVENT,data))
                })
    }

    override fun upData(list: MutableList<NewsTypeInfo>) {
        Observable.fromIterable(list)
                .doOnSubscribe {
                    mNewsDao.deleteAll()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    newsTypeInfoBean: NewsTypeInfo ->
                    newsTypeInfoBean.id = null
                    mNewsDao.save(newsTypeInfoBean)
                })
    }

    override fun swap(fromPos: Int, toPos: Int) {
        mRxBus.post(ChannelEvent(ChannelEvent.SWAP_EVENT,fromPos,toPos))
    }


}

