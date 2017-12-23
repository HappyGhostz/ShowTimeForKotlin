package com.example.happyghost.showtimeforkotlin.ui.video.play

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.IX5WebSettings
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_webview.*
import org.jetbrains.anko.startActivity

/**
 * @author Zhao Chenping
 * @creat 2017/12/23.
 * @description
 */
class DouyuWebViewPlayActivity : BaseActivity<IBasePresenter>() {
    private var callback: IX5WebChromeClient.CustomViewCallback? = null
    override fun upDataView() {
        img_back.setOnClickListener {
            finish()
        }
    }

    override fun initView() {
        val roomTitle = intent.getStringExtra(VIDEO_TITLE)
        var webUrl = intent.getStringExtra(VIDEO_URL)
        title = roomTitle
        initWebView()
        webUrl += "?from=dy"
        web_main.loadUrl(webUrl)
    }

    private fun initWebView() {
        val webSetting = web_main.settings
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(false)
        webSetting.loadWithOverviewMode = true
        webSetting.setAppCacheEnabled(true)
        //webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.setGeolocationEnabled(true)
        web_main.isDrawingCacheEnabled = true
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        webSetting.setAppCachePath(this.getDir("appcache", 0).path)
        webSetting.databasePath = this.getDir("databases", 0).path
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .path)
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        initWebViewPlay()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewPlay() {
        web_main.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView?, s: String?): Boolean {
                webView!!.loadUrl(s)
                return false
            }

        })
        web_main.setWebChromeClient(object : WebChromeClient() {
            /**
             * 全屏播放配置
             */
            override fun onShowCustomView(view: View?, customViewCallback: IX5WebChromeClient.CustomViewCallback?) {
                callback = customViewCallback
            }

            override fun onHideCustomView() {
                if (callback != null) {
                    callback!!.onCustomViewHidden()
                    callback = null
                }
                if (web_main != null) {
                    val viewGroup = web_main.parent as ViewGroup
                    viewGroup.removeView(web_main)
                }
            }

            override fun onReceivedTitle(arg0: WebView?, arg1: String?) {
                super.onReceivedTitle(arg0, arg1)
                tv_title.text = arg1

            }

            override fun onProgressChanged(webView: WebView?, i: Int) {
                super.onProgressChanged(webView, i)
                changeProgress(i)
            }
        })
    }

    private fun changeProgress(progress: Int) {
        if (progress in 0..99) {
            progressbar_webview.progress = progress
            progressbar_webview.visibility = View.VISIBLE
        } else if (progress == 100) {
            progressbar_webview.progress = 100
            progressbar_webview.visibility = View.GONE
        }
    }

    override fun initInjector() {
        //防止视频闪烁
        window.setFormat(PixelFormat.TRANSLUCENT)
    }

    override fun getContentView(): Int = R.layout.activity_webview
    companion object {
        var VIDEO_TITLE = "title"
        var VIDEO_URL="url"
        fun open(mContext: Context, room_name: String, jumpUrl: String) {
            mContext.startActivity<DouyuWebViewPlayActivity>(VIDEO_TITLE  to room_name,VIDEO_URL to jumpUrl)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (web_main.canGoBack()) {
            web_main.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onDestroy() {
        if (web_main != null)
            web_main.destroy()
        super.onDestroy()
    }
}