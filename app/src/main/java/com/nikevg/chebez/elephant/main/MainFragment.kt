package com.nikevg.chebez.elephant.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation.*
import android.view.animation.ScaleAnimation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nikevg.chebez.elephant.R
import com.nikevg.chebez.elephant.databinding.FragmentMainBinding
import com.nikevg.chebez.elephant.purchase.PurchaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val requestMicPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.listen()
        } else {
            viewModel.speak(getString(R.string.error_permission))
        }
    }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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
                    is PurchaseElephantEvent -> {
                        PurchaseActivity.start(context = requireContext())
                    }
                    is ShowElephantsEvent -> {
                        findNavController().navigate(MainFragmentDirections.actionToElephants())
                    }
                    is RecognitionResultsReadyEvent -> {
                        PurchaseActivity.start(
                            context = requireContext(),
                            phrase = event.results,
                            shouldSpeak = true
                        )
                    }
                    is UnknownErrorEvent -> {
                        viewModel.speak(getString(R.string.error_unknown))
                    }
                }
            }
        }

        ScaleAnimation(
            1f, 0.95f,
            1f, 0.95f,
            RELATIVE_TO_SELF, 0.5f,
            RELATIVE_TO_SELF, 0.5f,
        ).apply {
            repeatMode = REVERSE
            repeatCount = INFINITE
            duration = 2000
        }.let { animation ->
            binding.mainSaddle.startAnimation(animation)
        }

        return binding.root
    }

    private fun requestMicPermission() {
        if (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED) {
            viewModel.listen()
        } else {
            requestMicPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
}
