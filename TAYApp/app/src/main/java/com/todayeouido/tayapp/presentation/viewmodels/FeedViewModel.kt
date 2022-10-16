package com.todayeouido.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todayeouido.tayapp.data.remote.dto.bill.BillDto
import com.todayeouido.tayapp.data.remote.dto.bill.RecommendBillDto
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.todayeouido.tayapp.domain.model.toDomain
import com.todayeouido.tayapp.domain.use_case.GetHomeCommitteeBillUseCase
import com.todayeouido.tayapp.domain.use_case.GetMostViewedUseCase
import com.todayeouido.tayapp.domain.use_case.GetRecentBillUseCase
import com.todayeouido.tayapp.domain.use_case.GetRecommendBillUseCase
import com.todayeouido.tayapp.presentation.components.datalist
import com.todayeouido.tayapp.presentation.states.FeedUiState
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private val _selectedCategory = MutableStateFlow<Int?>(0)
    val selectedCategory: StateFlow<Int?> get() = _selectedCategory

    private val _isExpanded = MutableStateFlow<Boolean?>(false)
    val isExpanded: StateFlow<Boolean?> get() = _isExpanded

    private val _state = MutableStateFlow(FeedUiState())
    val state: StateFlow<FeedUiState> get() = _state

    private var _recommendBill = MutableStateFlow<List<RecommendBillDto>>(emptyList())
    val recommendBill: StateFlow<List<RecommendBillDto>> get() = _recommendBill

    private var _recentBill = MutableStateFlow<List<BillDto>>(emptyList())
    val recentBill: StateFlow<List<BillDto>> get() = _recentBill

    //최근 법안을 이미 불러오고 있는중일 경우 취소
    private var getRecentBillJob: Job? = null
    //최근 이슈법안을 이미 불러오고 있는중일 경우 취소
    private var getMostViewedBillJob: Job? = null

    var page = MutableStateFlow<Int>(1)
        private set
    var endReached = MutableStateFlow<Boolean>(false)
        private set
    var pagingLoading = MutableStateFlow<Boolean>(false)
        private set

    init {
        getMostViewed()
        getRecentBill()
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

    fun tryGetRecentBill(){
        if(_selectedCategory.value == 0){
            getRecentBill()
        }else{
            getBillCommittee()
        }
    }

    private fun getMostViewed() {
        getMostViewedBillJob?.cancel()
        getMostViewedBillJob = getMostViewedUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FeedUiState(
                        bill = result.data ?: emptyList()
                    )
                    endReached.value = false
                }
                is Resource.Error -> {
                    _state.value =
                        FeedUiState(error = result.message ?: "An unexpected error")
                    endReached.value = true
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

    private fun getRecentBill(){
        getRecentBillJob?.cancel()
        getRecentBillJob = getRecentBillUseCase(page.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _recentBill.value = _recentBill.value + result.data as List<BillDto>
                    page.value++
                    endReached.value= false
                    pagingLoading.value = false
                }
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
                is Resource.Loading -> {
                    pagingLoading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onCategorySelected(category: Int) {
        _selectedCategory.value = category
        page.value = 1
        endReached.value = false
        _recentBill.value = emptyList()
        if(_selectedCategory.value == 0){
            getMostViewed()
            getRecentBill()
        }else{
            getBillCommittee()
        }
    }

    private fun getBillCommittee(){
        getRecentBillJob?.cancel()
        getMostViewedBillJob?.cancel()
        getRecentBillJob = getHomeCommitteeBillUseCase(page.value, datalist[selectedCategory.value!!][0]).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _recentBill.value = _recentBill.value + result.data?.recentCreatedBill as List<BillDto>
                    page.value++
                    _state.update {
                        it.copy(
                            bill = result.data?.mostViewedBill?.map{it.toDomain()} ?: emptyList(),
                            isLoading = false
                        )
                    }
                    endReached.value= false
                    pagingLoading.value = false
                }
                is Resource.Error -> {
                    _state.value =
                        FeedUiState(error = result.message ?: "An unexpected error")
                    endReached.value = true
                }
                is Resource.Loading -> {
                    pagingLoading.value = true
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
