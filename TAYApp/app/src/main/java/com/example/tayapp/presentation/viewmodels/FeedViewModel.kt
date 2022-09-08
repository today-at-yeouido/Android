package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.tayapp.data.remote.dto.bill.RecommendBillDto
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.use_case.GetHomeCommitteeBillUseCase
import com.example.tayapp.domain.use_case.GetMostViewedUseCase
import com.example.tayapp.domain.use_case.GetRecentBillUseCase
import com.example.tayapp.domain.use_case.GetRecommendBillUseCase
import com.example.tayapp.presentation.states.FeedUiState
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject
constructor(
    private val getMostViewedUseCase: GetMostViewedUseCase,
    private val getRecentBillUseCase: GetRecentBillUseCase,
    private val getRecommendBillUseCase: GetRecommendBillUseCase,
    private val getHomeCommitteeBillUseCase: GetHomeCommitteeBillUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<String?>("전체")
    val selectedCategory: StateFlow<String?> get() = _selectedCategory

    private val _isExpanded = MutableStateFlow<Boolean?>(false)
    val isExpanded: StateFlow<Boolean?> get() = _isExpanded

    private val _state = MutableStateFlow(FeedUiState())
    val state: StateFlow<FeedUiState> get() = _state

    var recentBill = getRecentBillUseCase().cachedIn(viewModelScope)

    private var _recommendBill = MutableStateFlow<List<RecommendBillDto>>(emptyList())
    val recommendBill: StateFlow<List<RecommendBillDto>> get() = _recommendBill

    private var _recentBill = MutableStateFlow<List<RecommendBillDto>>(emptyList())
    val recentBill2: StateFlow<List<RecommendBillDto>> get() = _recentBill

    init {
        getMostViewed()
        if(UserState.isLogin()){
            getRecommendBill()
        }
    }

    fun tryGetMostViewed() {
        getMostViewed()
    }

    fun tryRecommendBill() {
        getRecommendBill()
    }

    fun tryGetCommitteeBill(){
        getBillCommittee()
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
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRecommendBill(){
        getRecommendBillUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                   _recommendBill.value = result.data?: emptyList()
                }
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        tryGetCommitteeBill()
    }

    private fun getBillCommittee(){
        getHomeCommitteeBillUseCase(1,"정무위원회").onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            bill = result.data?.mostViewedBill?.map{it.toDomain()} ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.value =
                        FeedUiState(error = result.message ?: "An unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = FeedUiState(isLoading = true)
                }
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onExpandChange(isExpanded: Boolean) {
        _isExpanded.value = isExpanded
    }
}
