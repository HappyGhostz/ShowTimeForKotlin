package com.example.happyghost.showtimeforkotlin.home

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.wegit.ProgressCustomView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() ,ProgressCustomView.PCviewOnClickListener{
    var mHasOpen:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
        iniData()

    }
    fun initView(){
        val imageView = find<ImageView>(R.id.iv_splash)
        ImageLoader.loadImageFromRes(this,R.mipmap.splash,imageView)
    }
    fun iniData(){
        downtime.startAnimation()
        downtime.setPCviewOnClickListener(this)
        downTime(5)
                //object 匿名类可以继承并超越 Java 中匿名类而实现多个接口
                .subscribe(object : Observer<Int>{
                    override fun onComplete() = startHomeActivity()
                    override fun onNext(t: Int) {
//                        Log.e("splash",t.toString())
                        downtime.setmText("倒计时:"+t)
                        downtime.setmTextColor(Color.parseColor("#000000"));
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


