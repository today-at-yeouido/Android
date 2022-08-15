package com.example.tayapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tayapp.domain.model.MostViewedBill
import com.example.tayapp.domain.use_case.GetMostViewedUseCase
import com.example.tayapp.domain.use_case.GetRecentBillUseCase
import com.example.tayapp.presentation.states.FeedUiState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject
constructor(
    private val getMostViewedUseCase: GetMostViewedUseCase,
    private val getRecentBillUseCase: GetRecentBillUseCase
) : ViewModel(){

    private val _selectedCategory = MutableStateFlow<String?>("전체")
    val selectedCategory: StateFlow<String?> get() = _selectedCategory

    private val _isExpanded = MutableStateFlow<Boolean?>(false)
    val isExpanded: StateFlow<Boolean?> get() = _isExpanded

    private val _state = MutableStateFlow(FeedUiState())
    val state: StateFlow<FeedUiState> get() = _state
    val recentBill = getRecentBillUseCase().cachedIn(viewModelScope)

    init {
        getMostViewed()
    }

    private fun getMostViewed() {
        getMostViewedUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FeedUiState(
                        bill = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value =
                        FeedUiState(error = result.message ?: "An unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = FeedUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value= category
    }

    fun onExpandChange(isExpanded: Boolean) {
        _isExpanded.value= isExpanded
    }
}