package com.nikevg.chebez.elephant.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val viewEventChannel = Channel<ViewEvent>()
    val viewEvents: Flow<ViewEvent> = viewEventChannel.receiveAsFlow()

    fun postEvent(event: ViewEvent) {
        viewModelScope.launch {
            viewEventChannel.send(event)
        }
    }
}
