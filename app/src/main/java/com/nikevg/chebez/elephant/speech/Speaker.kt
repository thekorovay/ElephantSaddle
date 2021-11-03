package com.nikevg.chebez.elephant.speech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import javax.inject.Inject

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
