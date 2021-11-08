package com.nikevg.chebez.elephant.elephants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nikevg.chebez.elephant.R
import com.nikevg.chebez.elephant.databinding.FragmentElephantsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElephantsFragment : Fragment() {

    private lateinit var binding: FragmentElephantsBinding
    private val viewModel: ElephantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_elephants,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.elephantsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}
