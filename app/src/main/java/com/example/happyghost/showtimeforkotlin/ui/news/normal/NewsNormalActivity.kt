package com.example.happyghost.showtimeforkotlin.ui.news.normal

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewConfiguration
import android.view.ViewStub

import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.NewsDetailInfo
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerNewsNormalComponent
import com.example.happyghost.showtimeforkotlin.inject.module.NewsNormalModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseSwipeBackActivity
import com.example.happyghost.showtimeforkotlin.utils.*
import com.example.happyghost.showtimeforkotlin.wegit.PullScrollView
import com.flyco.animation.SlideEnter.SlideBottomEnter
import com.flyco.animation.SlideEnter.SlideLeftEnter
import com.flyco.animation.SlideExit.SlideRightExit
import com.flyco.animation.SlideExit.SlideTopExit
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_news_normal.*
import kotlinx.android.synthetic.main.layout_comment_empty.*
import kotlinx.android.synthetic.main.layout_related_content.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity


class NewsNormalActivity : BaseSwipeBackActivity<NewsNormalPresenter>(),INewsNormalView {
    var mTopBarAnimator : Animator? = null
    private var mLastScrollY = 0
    var mNextNewsId: String? = null
    override fun loadData(newsDetailBean: NewsDetailInfo) {
         tv_title.setText(newsDetailBean.getTitle())
        tv_title_2.setText(newsDetailBean.getTitle())
        tv_time.setText(newsDetailBean.getPtime())
        RichText.from(newsDetailBean.getBody())
                .into(tv_content)
        handleSpInfo(newsDetailBean.getSpinfo())
        handleRelatedNews(newsDetailBean)
    }
    /**
     * 处理关联的内容
     *
     * @param spinfo
     */
    fun handleSpInfo(spinfo: List<NewsDetailInfo.SpinfoEntity>?) {
        if(!ListUtils.isEmpty(spinfo)){
            val stub = find<ViewStub>(R.id.vs_related_content)
            assert(stub!=null)
            stub.inflate()
            tv_type.setText(spinfo?.get(0)?.sptype)
            RichText.from(spinfo?.get(0)?.spcontent)
                    .urlClick {
                        val newsIdFromUrl = NewsUtils.clipNewsIdFromUrl(it)
                        if(newsIdFromUrl!=null){
                            lunch(this@NewsNormalActivity,newsIdFromUrl)
                        }
                        return@urlClick true
                    }
                    .into(tv_related_content)

        }
    }

    /**
     * 处理关联新闻
     */
    fun handleRelatedNews(newsDetailBean: NewsDetailInfo) {
        if(ListUtils.isEmpty(newsDetailBean.getRelative_sys())){
            tv_next_title.setText("没有相关文章了")
        }else{
            mNextNewsId = newsDetailBean.getRelative_sys()?.get(0)?.getId()!!
            tv_next_title.setText(newsDetailBean.getRelative_sys()!!.get(0).getTitle())
        }
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        val mToolBarHeight = resources.getDimensionPixelSize(R.dimen.news_detail_toolbar_height)
        val mTopBarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height)
        val mMinScrollSlop = ViewConfiguration.get(this).scaledTouchSlop
        ViewCompat.setTranslationY(ll_top_bar, (-mTopBarHeight).toFloat())
        scroll_view.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if(scrollY>mToolBarHeight){
                    if(AnimationUtils.isRunning(mTopBarAnimator)){
                         return
                    }
                    if(Math.abs(scrollY-mLastScrollY)>mMinScrollSlop){
                        var isPullUp = scrollY>mLastScrollY
                        mLastScrollY = scrollY
                        if(isPullUp&& ll_top_bar.translationY!=-mTopBarHeight.toFloat()){
                            mTopBarAnimator = AnimationUtils.doMoveVertical(ll_top_bar, ll_top_bar.translationY,
                                    -mTopBarHeight.toFloat(), 300)
                        }else if(!isPullUp && ll_top_bar.translationY.toInt()!=0){
                            mTopBarAnimator = AnimationUtils.doMoveVertical(ll_top_bar, ll_top_bar.translationY,
                                    0f, 300)
                        }
                    }
                }else{
                    if(ll_top_bar.translationY.toInt()!=-mTopBarHeight){
                        AnimationUtils.stopAnimator(mTopBarAnimator)
                        ViewCompat.setTranslationY(ll_top_bar,-mTopBarHeight.toFloat())
                        mLastScrollY =0
                    }
                }
            }
        })
        scroll_view.setFootView(ll_foot_view)
        scroll_view.setOnPullListener(object :PullScrollView.OnPullListener{
            var isShowPop = PreferencesUtils.getBoolean(ConsTantUtils.SHOW_POPUP_DETAIL, true)
            override fun isDoPull(): Boolean {
                if(empty_comment.getEmptyStatus()!=2){
                    return false
                }
                if(isShowPop){
                    showPop()
                    isShowPop = false
                }
                return true
            }

            override fun handlePull(): Boolean {
                if(TextUtils.isEmpty(mNextNewsId)){
                    return false
                }else{
                    lunchInSide(mNextNewsId!!)
                    return true
                }
            }
        })

         iv_back.setOnClickListener { finish() }
        iv_back_2.setOnClickListener { finish() }
        tv_title_2.setOnClickListener {
            scroll_view.stopNestedScroll()
            scroll_view.smoothScrollTo(0, 0)
        }
    }

    override fun initInjector() {
        val normalId = intent.getStringExtra(NEWS_NORMAL_ID)
        DaggerNewsNormalComponent.builder()
                .applicationComponent(getAppComponent())
                .newsNormalModule(NewsNormalModule(this,normalId))
                .build()
                .inject(this)

    }

    override fun getContentView(): Int {
       return R.layout.activity_news_normal
    }

    companion object {
        var NEWS_NORMAL_ID:String="newsnormalid"
        fun lunch(context: Context,postid:String){
            context.startActivity<NewsNormalActivity>(NEWS_NORMAL_ID to postid)
            (context as Activity).overridePendingTransition(R.anim.slide_right_entry, R.anim.fade_exit)
        }
    }
    fun lunchInSide(newsId:String){
        finish()
        startActivity<NewsNormalActivity>(NEWS_NORMAL_ID to newsId)
        overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold)
    }
    /**
     * 显示弹出提示
     */
    fun showPop(){
        if(PreferencesUtils.getBoolean(ConsTantUtils.SHOW_POPUP_DETAIL,true)){
            DialogHelper.creatPopup(this, R.layout.layout_popup)
                    .anchorView(tv_title_2)
                    .gravity(Gravity.BOTTOM)
                    .showAnim(SlideBottomEnter())
                    .dismissAnim(SlideTopExit())
                    .autoDismiss(true)
                    .autoDismissDelay(3500)
                    .show()
            DialogHelper.creatPopup(this, R.layout.layout_popup_bottom)
                    .anchorView(ll_foot_view)
                    .gravity(Gravity.TOP)
                    .showAnim(SlideLeftEnter())
                    .dismissAnim(SlideRightExit())
                    .autoDismiss(true)
                    .autoDismissDelay(3500)
                    .show()
            PreferencesUtils.putBoolean(ConsTantUtils.SHOW_POPUP_DETAIL, false)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit)
    }
}
