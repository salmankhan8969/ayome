package com.example.util

import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log

class BountySoundManager {
    private var toneGenerator: ToneGenerator? = null
    private var isMuted = false

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
        } catch (e: Exception) {
            Log.e("BountySoundManager", "ToneGenerator init failed", e)
        }
    }

    fun setMuted(muted: Boolean) {
        isMuted = muted
    }

    fun isMuted(): Boolean = isMuted

    fun playChipSound() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 30)
        } catch (_: Exception) {}
    }

    fun playWheelTick() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 25)
        } catch (_: Exception) {}
    }

    fun playWinSound() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 300)
        } catch (_: Exception) {}
    }

    fun playJackpotSound() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 400)
        } catch (_: Exception) {}
    }

    fun playCountdownWarning() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 60)
        } catch (_: Exception) {}
    }

    fun release() {
        try {
            toneGenerator?.release()
            toneGenerator = null
        } catch (_: Exception) {}
    }
}
