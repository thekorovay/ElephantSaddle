package com.nikevg.chebez.elephant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.RESULTS_RECOGNITION
import android.speech.tts.TextToSpeech
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.nikevg.chebez.elephant.databinding.ActivityMainBinding
import com.nikevg.chebez.elephant.extensions.setRecognitionCallbacks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import java.util.*

/**
 * Пока ничего кроме этого не делал, проект не чистил. Инструменты и архитектуру
 * (громко сказано, соглы) надо будет обсудить. Но TTS и STT уже работают
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var speaker: TextToSpeech? = null
    private var recognizer: SpeechRecognizer? = null
    private val isRecording = MutableStateFlow(false)
    private val locale = Locale.forLanguageTag(LANGUAGE_TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initSpeaker()
        initRecognizer()
    }

    /**
     * !!! Если после предоставления разрешений все равно возвращает ошибку 9 -
     * надо предоставить разрешение на микрофон приложению Google
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RECORD_PERMISSION_CODE) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                startListening()
            } else {
                binding.speechToTextValue = getString(R.string.error_permission)
            }
        }
    }

    private fun initRecognizer() {
        // Уродски выглядит, когда будем уносить все что надо из активити во вьюмодель,
        // нужна будет или LiveData, или альфа (или бета, не помню) библиотека
        // (то ли корутин, то ли лайфсайкла, не помню), чтобы прямо передавать флоу в байндинг
        lifecycleScope.launchWhenStarted {
            isRecording.collect { isRec ->
                binding.isRecording = isRec
            }
        }

        recognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionCallbacks(
                onReadyForSpeech = {
                    binding.speechToTextValue = getString(R.string.listening)
                    isRecording.value = true
                },
                onBeginningOfSpeech = {
                    binding.speechToTextValue = ""
                },
                onPartialResults = { results ->
                    results?.getStringArrayList(RESULTS_RECOGNITION)?.let { text ->
                        binding.speechToTextValue = text.joinToString(separator = "\n")
                    }
                },
                onResults = { results ->
                    this@MainActivity.stopListening()
                    results?.getStringArrayList(RESULTS_RECOGNITION)?.let { text ->
                        val readyString = text.joinToString(separator = "\n")
                        binding.speechToTextValue = readyString
                        speak(readyString)
                    }
                },
                onError = { errorCode ->
                    this@MainActivity.stopListening()
                    // Чекай коды ошибок на https://developer.android.com/reference/android/speech/SpeechRecognizer#constants_1
                    // todo Добавить норм сообщения об ошибках
                    binding.speechToTextValue = getString(R.string.error_with_code, errorCode)
                }
            )
        }

        binding.speechToTextStartStop.setOnClickListener {
            if (isRecording.value) {
                recognizer?.stopListening()
            } else {
                requestListening()
            }
        }
    }

    private fun initSpeaker() {
        speaker = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                speaker?.language = locale
            } else {
                binding.speechToTextValue = getString(R.string.error_init_speaker)
            }
        }
    }

    private fun requestListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        } else {
            startListening()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_PERMISSION_CODE
            )
        }
    }

    private fun speak(text: String) {
        speaker?.speak(
            getString(R.string.omega_phrase, text),
            TextToSpeech.QUEUE_FLUSH,
            Bundle(),
            RECORD_TAG
        )
    }

    private fun startListening() {
        val speechRecognizerIntent = Intent(ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
            putExtra(EXTRA_LANGUAGE, locale)
        }

        recognizer?.run {
            cancel()
            startListening(speechRecognizerIntent)
        }
    }

    private fun stopListening() {
        isRecording.value = false
    }

    companion object {
        const val RECORD_PERMISSION_CODE = 0
        const val LANGUAGE_TAG = "RU"
        const val RECORD_TAG = "absolutelySameTagEveryTime"
    }
}
