package com.example.happyghost.showtimeforkotlin.ui.music.rank

import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListItem
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.support.v4.toast

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
class MusicRankPresenter(view: MusicRankFragment) :IBasePresenter {
    var mView = view
    override fun getData() {
       RetrofitService.getRankMusicListAll(ConsTantUtils.MUSIC_URL_FORMAT,ConsTantUtils.MUSIC_URL_FROM,
               ConsTantUtils.MUSIC_URL_METHOD_RANKINGLIST,ConsTantUtils.MUSIC_URL_RANKINGLIST_FLAG)
               .doOnSubscribe { mView.showLoading() }
               .compose(mView.bindToLife())
               .subscribe(object:Observer<RankingListItem>{
                   override fun onComplete() {
                       mView.hideLoading()
                   }

                   override fun onSubscribe(d: Disposable) {
                   }

                   override fun onError(e: Throwable) {
                       mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                           override fun onReTry() {
                               getData()
                           }
                       })
                   }

                   override fun onNext(t: RankingListItem) {
                       val content = t.getContent()
                       if(content!=null){
                           mView.loadListMusic(content)
                       }else{
                           mView.toast("程序出错!")
                       }

                   }
               })
    }

    override fun getMoreData() {

    }
}