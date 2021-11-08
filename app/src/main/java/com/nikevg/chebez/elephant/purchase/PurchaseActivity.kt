package com.nikevg.chebez.elephant.purchase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.nikevg.chebez.elephant.R
import com.nikevg.chebez.elephant.databinding.ActivityPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBinding
    private val viewModel: PurchaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        binding.lifecycleOwner = this

        if (intent.getBooleanExtra(EXTRA_SHOULD_SPEAK, false)) {
            intent.getStringExtra(EXTRA_PHRASE).let { phrase ->
                if (phrase.isNullOrBlank()) {
                    viewModel.speak(getString(R.string.omega_phrase_on_silence))
                } else {
                    viewModel.speak(getString(R.string.omega_phrase, phrase))
                }
            }
        }
    }

    companion object {
        const val EXTRA_PHRASE = "extra_phrase"
        const val EXTRA_SHOULD_SPEAK = "extra_should_speak"

        // todo Красивишно, но, возможно, сложнишно для понимания. Оставляю за тобой выбор,
        //  оставить так, или унести создание и вызов интента в MainFragment
        fun start(context: Context, phrase: String? = null, shouldSpeak: Boolean = false) {
            val intent = Intent(context, PurchaseActivity::class.java).apply {
                putExtra(EXTRA_PHRASE, phrase)
                putExtra(EXTRA_SHOULD_SPEAK, shouldSpeak)
            }
            context.startActivity(intent)
        }
    }
}
