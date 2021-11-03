package com.nikevg.chebez.elephant.main

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.nikevg.chebez.elephant.R
import com.nikevg.chebez.elephant.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val requestMicPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.listen()
        } else {
            viewModel.speak(getString(R.string.error_permission))
        }
    }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        lifecycleScope.launchWhenStarted {
            viewModel.viewEvents.collect { event ->
                when (event) {
                    is CheckMicPermissionEvent -> {
                        requestMicPermission()
                    }
                    is MicPermissionDeniedErrorEvent -> {
                        viewModel.speak(getString(R.string.error_permission))
                    }
                    is NetworkErrorEvent -> {
                        viewModel.speak(getString(R.string.error_network))
                    }
                    is RecognitionResultsReadyEvent -> {
                        if (event.results == null) {
                            viewModel.speak(getString(R.string.omega_phrase_on_silence))
                        } else {
                            viewModel.speak(getString(R.string.omega_phrase, event.results))
                        }
                    }
                    is UnknownErrorEvent -> {
                        viewModel.speak(getString(R.string.error_unknown))
                    }
                }
            }
        }
    }

    private fun requestMicPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED) {
            viewModel.listen()
        } else {
            requestMicPermissionLauncher.launch(RECORD_AUDIO)
        }
    }
}
