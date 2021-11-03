package com.nikevg.chebez.elephant.extensions

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer

fun SpeechRecognizer.setRecognitionCallbacks(
    onReadyForSpeech: () -> Unit,
    onBeginningOfSpeech: () -> Unit,
    onPartialResults: (results: Bundle?) -> Unit,
    onResults: (results: Bundle?) -> Unit,
    onError: (code: Int) -> Unit
) {
    setRecognitionListener(
        object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                onReadyForSpeech()
            }

            override fun onBeginningOfSpeech() {
                onBeginningOfSpeech()
            }

            override fun onError(code: Int) {
                onError(code)
            }

            override fun onResults(results: Bundle?) {
                onResults(results)
            }

            override fun onPartialResults(results: Bundle?) {
                onPartialResults(results)
            }

            override fun onEvent(p0: Int, p1: Bundle?) { }

            override fun onRmsChanged(p0: Float) { }

            override fun onBufferReceived(p0: ByteArray?) { }

            override fun onEndOfSpeech() { }
        }
    )
}
