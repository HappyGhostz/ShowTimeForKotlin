package com.example.happyghost.showtimeforkotlin.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 */
abstract class BaseSwipeBackActivity<T:IBasePresenter>: BaseActivity<T>() {
    lateinit var swipeBackLayout: SwipeBackLayout
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        // 触摸边缘变为屏幕宽度的1/2
        swipeBackLayout.setEdgeSize(resources.displayMetrics.widthPixels)
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
        swipeBackLayout.setEnableGesture(true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        this.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        this.getWindow().getDecorView().setBackgroundDrawable(null)
        swipeBackLayout = LayoutInflater.from(this)
                .inflate(me.imid.swipebacklayout.lib.R.layout.swipeback_layout, null) as SwipeBackLayout
        swipeBackLayout.attachToActivity(this)
    }

}