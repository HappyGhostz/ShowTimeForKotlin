package com.example.happyghost.showtimeforkotlin.ui.home

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.wegit.ProgressCustomView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<IBasePresenter>() ,ProgressCustomView.PCviewOnClickListener{
    override fun upDataView() {
        iniData()
    }

    override fun initView() {
        val imageView = find<SimpleDraweeView>(R.id.iv_splash)
        //fresco加载动图和OOM问题处理确实比Glide给力太多
        //fresco中文文档https://www.fresco-cn.org/docs/drawee-branches.html
        var  controller = Fresco.newDraweeControllerBuilder()
                .setUri("res:// /"+R.mipmap.splash)
                .setAutoPlayAnimations(true)
                .build();
        imageView.controller = controller
//        ImageLoader.loadImageFromRes(this,R.mipmap.splash,imageView)
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int = R.layout.activity_splash

    var mHasOpen:Boolean=false
    fun iniData(){
        downtime.startAnimation()
        downtime.setPCviewOnClickListener(this)
        downTime(5)
                .compose(this.bindToLife())
                //object 匿名类可以继承并超越 Java 中匿名类而实现多个接口
                .subscribe(object : Observer<Int>{
                    override fun onComplete() = startHomeActivity()
                    override fun onNext(t: Int) {
//                        Log.e("splash",t.toString())
                        downtime.setmText("倒计时:"+t)
                        downtime.setmTextColor(Color.parseColor("#000000"))
                    }

                    override fun onError(e: Throwable) {
                        startHomeActivity()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })

    }
    fun startHomeActivity(){
        if(!mHasOpen){
            mHasOpen=true
            startActivity<MainActivity>()
            overridePendingTransition(R.anim.fade_entry,R.anim.fade_exit)
            finish()
        }
    }

    override fun onClick() {
        startHomeActivity()
    }

    fun downTime(time:Int):Observable<Int>{
        var downTime:Int = time
        return Observable.interval(0,1,TimeUnit.SECONDS)
                .map(Function<Long,Int> { return@Function (downTime-it.toInt()) })
                .take((time+1).toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

}


