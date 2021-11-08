package com.nikevg.chebez.elephant.purchase

import com.nikevg.chebez.elephant.base.BaseViewModel
import com.nikevg.chebez.elephant.speech.Speaker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val speaker: Speaker
) : BaseViewModel() {

    fun speak(text: String) {
        speaker.speak(text)
    }
}
