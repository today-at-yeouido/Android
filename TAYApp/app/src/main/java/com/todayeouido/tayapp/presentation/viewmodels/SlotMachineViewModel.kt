package com.todayeouido.tayapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SlotMachineViewModel @Inject constructor(): ViewModel()  {

    val seconds = (0..100)
        .asSequence()
        .asFlow()
        .onEach { delay(2400) }
}