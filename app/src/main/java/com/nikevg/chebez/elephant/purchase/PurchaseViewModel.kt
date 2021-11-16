package com.nikevg.chebez.elephant.purchase

import androidx.lifecycle.viewModelScope
import com.nikevg.chebez.elephant.base.BaseViewModel
import com.nikevg.chebez.elephant.database.Elephant
import com.nikevg.chebez.elephant.database.ElephantDatabase
import com.nikevg.chebez.elephant.elephants_data_mock.ElephantSource
import com.nikevg.chebez.elephant.speech.Speaker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val speaker: Speaker,
    private val elephantDatabase: ElephantDatabase
) : BaseViewModel() {

    val elephants = ElephantSource.generateElephants()

    fun speak(text: String) {
        speaker.speak(text)
    }

    fun buyElephant(elephant: Elephant) {
        viewModelScope.launch(Dispatchers.IO) {
            elephantDatabase.elephantDao().insert(elephant)
        }
    }
}
