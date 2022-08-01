package com.example.tayapp.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.GetMostViewedUseCase
import com.example.tayapp.presentation.states.MostViewedUiState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getMostViewedUseCase: GetMostViewedUseCase)
    : ViewModel() {

    private val _state = MutableStateFlow(MostViewedUiState())
    val state: StateFlow<MostViewedUiState> get() =  _state

    init {
        getMostViewed()
    }

    private fun getMostViewed() {
        getMostViewedUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MostViewedUiState(bill = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = MostViewedUiState(error = result.message ?:"An unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = MostViewedUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}