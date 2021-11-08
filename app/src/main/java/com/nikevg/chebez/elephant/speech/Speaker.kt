package com.nikevg.chebez.elephant.speech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import javax.inject.Inject
import javax.inject.Singleton

/*
* Спикер используется в двух активити, и в случае, например, когда в
* MainActivity еще воспроизводится сообщение об ошибке, и был переход в PurchaseActivity
* через прослушивание фразы, воспроизведение в MainActivity надо остановить (да и
* TextToSpeechService надо освободить), проще всего это сделать, объявив спикер синглтоном
*/
@Singleton
class Speaker @Inject constructor(
    private val textToSpeech: TextToSpeech
) {

    fun speak(text: String) {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }

        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            Bundle(),
            RECORD_TAG
        )
    }

    companion object {
        const val RECORD_TAG = "absolutelySameTagEveryTime"
    }
}
