package com.nikevg.chebez.elephant.main

import android.speech.SpeechRecognizer.*
import com.nikevg.chebez.elephant.base.BaseViewModel
import com.nikevg.chebez.elephant.speech.Recognizer
import com.nikevg.chebez.elephant.speech.Speaker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val speaker: Speaker,
    private val recognizer: Recognizer
) : BaseViewModel() {

    private val mutableIsRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = mutableIsRecording

    init {
        recognizer.setRecognitionCallbacks(
            onReadyForSpeech = {
                mutableIsRecording.value = true
            },
            onResults = { results ->
                mutableIsRecording.value = false
                postEvent(RecognitionResultsReadyEvent(results))
            },
            onError = { errorCode ->
                mutableIsRecording.value = false
                // 1. Если после предоставления разрешений все равно возвращает
                // ошибку 9 - надо предоставить разрешение на микрофон приложению Google.
                // 2. Если ошибка 7 на эмуляторе, даже когда юзер не молчит -
                // в настройках эмулятора включить майк
                postEvent(
                    when (errorCode)  {
                        ERROR_NO_MATCH -> RecognitionResultsReadyEvent(results = null)
                        ERROR_INSUFFICIENT_PERMISSIONS -> MicPermissionDeniedErrorEvent
                        ERROR_NETWORK -> NetworkErrorEvent
                        else -> UnknownErrorEvent
                    }
                )
            }
        )
    }

    fun requestListening() {
        postEvent(CheckMicPermissionEvent)
    }

    fun stopListening() {
        recognizer.stopListening()
    }

    /**
     * Не вызывайте этот метод напрямую при нажатии на кнопку, только после подтверждения
     * выданных пользователем разрешений на использование микрофона.
     * Если пользователь не предоставит разрешения, вызов этого метода вернет
     * соответствующую ошибку и вызовет обработчик onError
     * из метода setRecognitionCallbacks объекта Recognizer.
     */
    fun listen() {
        recognizer.startListening()
        mutableIsRecording.value = true
    }

    fun speak(text: String) {
        speaker.speak(text)
    }

    fun purchaseElephant() {
        cancelListening()
        postEvent(PurchaseElephantEvent)
    }

    fun showElephants() {
        cancelListening()
        postEvent(ShowElephantsEvent)
    }

    private fun cancelListening() {
        recognizer.cancelListening()
        mutableIsRecording.value = false
    }
}
