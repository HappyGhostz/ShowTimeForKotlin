package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.os.Bundle
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.example.happyghost.showtimeforkotlin.bean.NewsInfo

/**
 * @author Zhao Chenping
 * @creat 2017/9/8.
 * @description
 */
class SliderHelper {
    companion object {
        private val SLIDER_KEY = "SliderKey"
        fun initAdSlider(mContext: Context?, sliderLayout: SliderLayout, newsBean: NewsInfo) {
            for (adData in newsBean.ads!!){
                val textSliderView = TextSliderView(mContext)
                textSliderView.description(adData.title)
                        .image(adData.imgsrc)
                        .empty(DefIconFactory.provideIcon())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener {
                            slider ->
                            if(slider.bundle!=null){
                                val adData = slider.bundle.getParcelable<NewsInfo.AdsBean>(SLIDER_KEY)
                                if(adData!=null){
                                    if(NewsUtils.isNewsPhotoSet(adData.tag)){
                                        //todo
                                    }else if(NewsUtils.isNewsSpecial(adData.tag)){
                                        //todo
                                    }else{
                                        //todo
                                    }
                                }
                            }

                        }
                textSliderView.bundle(Bundle())
                textSliderView.bundle.putParcelable(SLIDER_KEY,adData)
                sliderLayout.addSlider(textSliderView)

            }
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom)
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion)
            sliderLayout.setCustomAnimation(DescriptionAnimation())
            sliderLayout.setDuration(4000)
        }
    }
}