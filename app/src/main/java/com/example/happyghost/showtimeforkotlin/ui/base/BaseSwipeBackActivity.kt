package com.example.happyghost.showtimeforkotlin.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 */
abstract class BaseSwipeBackActivity<T:IBasePresenter>: BaseActivity<T>() {
    lateinit var swipeBackLayout: SwipeBackLayout
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        swipeBackLayout = LayoutInflater.from(this).inflate(
                me.imid.swipebacklayout.lib.R.layout.swipeback_layout, null) as SwipeBackLayout;
//        SwipeBackActivity
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        swipeBackLayout.attachToActivity(this)
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        // 触摸边缘变为屏幕宽度的1/2
        swipeBackLayout.setEdgeSize(resources.displayMetrics.widthPixels/2)
    }
}