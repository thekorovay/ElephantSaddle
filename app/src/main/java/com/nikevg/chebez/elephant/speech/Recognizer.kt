package com.nikevg.chebez.elephant.speech

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent.*
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.RESULTS_RECOGNITION
import java.util.*
import javax.inject.Inject

class Recognizer @Inject constructor(
    private val locale: Locale,
    private val speechRecognizer: SpeechRecognizer
) {

    private var ignoreRecognitionResults = false
    private var isListening = false

    fun setRecognitionCallbacks(
        onReadyForSpeech: () -> Unit,
        onResults: (results: String?) -> Unit,
        onError: (code: Int) -> Unit
    ) {
        speechRecognizer.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(p0: Bundle?) {
                    isListening = true
                    onReadyForSpeech()
                }

                override fun onError(code: Int) {
                    isListening = false
                    if (!ignoreRecognitionResults) {
                        onError(code)
                    }
                }

                override fun onResults(results: Bundle?) {
                    isListening = false
                    if (!ignoreRecognitionResults) {
                        onResults(results?.getStringArrayList(RESULTS_RECOGNITION)?.firstOrNull())
                    }
                }

                override fun onBeginningOfSpeech() { }

                override fun onBufferReceived(p0: ByteArray?) { }

                override fun onEndOfSpeech() { }

                override fun onEvent(p0: Int, p1: Bundle?) { }

                override fun onPartialResults(results: Bundle?) { }

                override fun onRmsChanged(p0: Float) { }
            }
        )
    }

    fun startListening() {
        ignoreRecognitionResults = false

        val speechRecognizerIntent = Intent(ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
            putExtra(EXTRA_LANGUAGE, locale)
        }

        speechRecognizer.startListening(speechRecognizerIntent)
    }

    fun stopListening() {
        // Если вызвать stopListening, когда запись не идет, то speech recognizer кинет ошибку 5,
        // которая вынесет тебе весь мозг, пока ты поймешь, в чем дело. Поэтому проверяем запись.
        if (isListening) {
            speechRecognizer.stopListening()
        }
    }

    fun cancelListening() {
        ignoreRecognitionResults = true
        stopListening()
    }
}
