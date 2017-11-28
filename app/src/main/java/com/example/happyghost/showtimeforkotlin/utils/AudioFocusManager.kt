package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.ui.music.play.MusicPlayService
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager



/**
 * @author Zhao Chenping
 * @creat 2017/11/28.
 * @description
 */
class AudioFocusManager : AudioManager.OnAudioFocusChangeListener {

    var mPlayService:MusicPlayService
    var mAudioManager:AudioManager
    private var isPausedByFocusLossTransient: Boolean = false
    private var mVolumeWhenFocusLossTransientCanDuck: Int = 0
    constructor(playService: MusicPlayService){
        mPlayService = MusicPlayService()
        mAudioManager = playService.getSystemService(AUDIO_SERVICE) as AudioManager
    }
    /**
     * 播放音乐前先请求音频焦点
     */
    fun requestAudioFocus():Boolean{
        val isRequest = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        return isRequest
    }

    /**
     * 退出播放器后不再占用音频焦点
     */
    fun abandonAudioFocus(){
        mAudioManager.abandonAudioFocus(this)
    }
    /**
     * 音频焦点监听回调
     */
    override fun onAudioFocusChange(focusChange: Int) {
        var volume = 0
        when (focusChange) {
            // 重新获得焦点
             AudioManager.AUDIOFOCUS_GAIN->{
                 if(!willPlay() && isPausedByFocusLossTransient){
                     // 通话结束，恢复播放
                     mPlayService.hasPlay()
                 }
                 volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                 if (mVolumeWhenFocusLossTransientCanDuck > 0 && volume == mVolumeWhenFocusLossTransientCanDuck / 2) {
                     // 恢复音量
                     mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolumeWhenFocusLossTransientCanDuck,
                             AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                 }
                 isPausedByFocusLossTransient = false
                 mVolumeWhenFocusLossTransientCanDuck = 0
             }
            // 永久丢失焦点，如被其他播放器抢占
             AudioManager.AUDIOFOCUS_LOSS->{
                 if (willPlay()) {
                     forceStop()
                 }
             }

            // 短暂丢失焦点，如来电
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT->{
                if (willPlay()) {
                    forceStop();
                    isPausedByFocusLossTransient = true
                }
            }

            // 瞬间丢失焦点，如通知
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK->{
                // 音量减小为一半
                volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (willPlay() && volume > 0) {
                    mVolumeWhenFocusLossTransientCanDuck = volume;
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolumeWhenFocusLossTransientCanDuck / 2,
                            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }
            }

        }
    }

    fun  willPlay():Boolean = mPlayService.isPrepare() || mPlayService.isPlaying()
    private fun forceStop() {
        if (mPlayService.isPrepare()) {
            mPlayService.playStop()
        } else if (mPlayService.isPlaying()) {
            mPlayService.playPause()
        }
    }

}