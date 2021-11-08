package com.nikevg.chebez.elephant.elephants

import androidx.lifecycle.viewModelScope
import com.nikevg.chebez.elephant.base.BaseViewModel
import com.nikevg.chebez.elephant.database.Elephant
import com.nikevg.chebez.elephant.database.ElephantDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ElephantsViewModel @Inject constructor(
    database: ElephantDatabase
) : BaseViewModel() {

    val elephants: StateFlow<List<Elephant>> = database.elephantDao().getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}
