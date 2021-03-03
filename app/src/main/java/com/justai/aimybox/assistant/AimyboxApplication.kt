package com.justai.aimybox.assistant

import android.app.Application
import android.content.Context
import com.justai.aimybox.Aimybox
import com.justai.aimybox.api.aimybox.AimyboxDialogApi
import com.justai.aimybox.components.AimyboxProvider
import com.justai.aimybox.core.Config
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import java.util.*
import com.justai.aimybox.speechkit.kaldi.KaldiAssets
import com.justai.aimybox.speechkit.kaldi.KaldiSpeechToText
import com.justai.aimybox.speechkit.kaldi.KaldiVoiceTrigger

class AimyboxApplication : Application(), AimyboxProvider {

    companion object {
        private const val AIMYBOX_API_KEY = "Ldf0j7WZi3KwNah2aNeXVIACz0lb9qMH"
    }

    override val aimybox by lazy { createAimybox(this) }

    private fun createAimybox(context: Context): Aimybox {
        val unitId = UUID.randomUUID().toString()
        val assets = KaldiAssets.fromApkAssets(this, "model")
        val voiceTrigger = KaldiVoiceTrigger(assets, listOf("listen", "hey"))
        //val speechToText = KaldiSpeechToText(assets)
        val speechToText = GooglePlatformSpeechToText(context, Locale.getDefault())
        val textToSpeech = GooglePlatformTextToSpeech(context, Locale.getDefault()) // or any other TTS

        val dialogApi = AimyboxDialogApi(AIMYBOX_API_KEY, unitId)

        //return Aimybox(Config.create(speechToText, textToSpeech, dialogApi))
        return Aimybox(Config.create(speechToText, textToSpeech, dialogApi) {
            this.voiceTrigger = voiceTrigger
        })
    }
}