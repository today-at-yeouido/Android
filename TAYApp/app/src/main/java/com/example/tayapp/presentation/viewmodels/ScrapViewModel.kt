package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.scrap.GetScrapUseCase
import com.example.tayapp.domain.use_case.scrap.PostAddScrapUseCase
import com.example.tayapp.domain.use_case.scrap.PostDeleteScrapUseCase
import com.example.tayapp.presentation.states.ScrapState
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val postAddScrapUseCase: PostAddScrapUseCase,
    private val postDeleteScrapUseCase: PostDeleteScrapUseCase,
    private val getScrapUseCase: GetScrapUseCase
) : ViewModel() {

    private var _scrapState = MutableStateFlow(ScrapState(isLoading = true))
    val scrapState = _scrapState

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing


    init {
        getScrapBill()
    }

    private fun getScrapBill() {
        if (UserState.isLogin()) {
            getScrapUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _scrapState.update {
                            it.copy(bill = result.data ?: emptyList(), isLoading = false)
                        }

                    }
                    is Resource.Error -> {
                        _scrapState.update {
                            it.copy(
                                error = result.message ?: "An unexpected error",
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _scrapState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.NetworkConnectionError -> {
                        UserState.network = false
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    fun deleteScrap(bill: Int) {
        viewModelScope.launch {
            postDeleteScrapUseCase(bill).collect() { it ->
                when (it) {
                    is Resource.Success -> Log.d("스크랩", "스크랩 취소 성공")
                    is Resource.Error -> Log.d("스크랩", "스크랩 취소 실패")
                    is Resource.Loading -> Log.d("스크랩", "스크랩 취소 올리는중")
                }
            }
        }
    }


    fun refresh() {
        _isRefreshing.value = true
        getScrapBill()
        _isRefreshing.value = false
    }
}