package com.example.happyghost.showtimeforkotlin.ui.music.list

import com.example.happyghost.showtimeforkotlin.bean.musicdate.WrapperSongListInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
class MusicListPresenter(view: MusicListFragment) :IBasePresenter {
    var mView = view
    private var mStartPage = 0
    override fun getData() {
        mStartPage = 0
        RetrofitService.getMusicListAll(ConsTantUtils.MUSIC_URL_FORMAT,ConsTantUtils.MUSIC_URL_FROM,
                ConsTantUtils.MUSIC_URL_METHOD_GEDAN,ConsTantUtils.pageSize,mStartPage)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object : Observer<WrapperSongListInfo>{
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object:EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onNext(t: WrapperSongListInfo) {
                        mView.loadListMusic(t.getContent()!!)
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }
                })
    }

    override fun getMoreData() {

    }
}