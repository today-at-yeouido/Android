package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.Article
import com.example.tayapp.domain.use_case.GetBillDetailUseCase
import com.example.tayapp.domain.use_case.GetBillTableUseCase
import com.example.tayapp.domain.use_case.scrap.PostAddScrapUseCase
import com.example.tayapp.domain.use_case.scrap.PostDeleteScrapUseCase
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.states.BillDetailUiState
import com.example.tayapp.presentation.states.BillTableUiState
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postAddScrapUseCase: PostAddScrapUseCase,
    private val postDeleteScrapUseCase: PostDeleteScrapUseCase,
    private val getBillDetailUseCase: GetBillDetailUseCase,
    private val getBillTableUseCase: GetBillTableUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel()  {

    val billId: Int = savedStateHandle.get<String>(Destinations.BILL_ID)?.toInt()!!
    var detailState = MutableStateFlow(BillDetailUiState(isLoading = true))
        private set
    var table = MutableStateFlow(BillTableUiState(isLoading = true))
        private set

    init {
        initFunction()
    }

    private fun initFunction() {
        viewModelScope.launch {
            getBillDetail(billId)
            getBillTable(billId)
        }
    }

    fun tryGetBillDetail() {
        viewModelScope.launch {
            getBillDetail(billId)
        }
    }

    private suspend fun getBillTable(billId: Int) {
        getBillTableUseCase(billId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    table.update {
                        it.copy(billTable = result.data, isLoading = false)
                    }
                    result.data?.condolences?.forEach {
                        if(it is Article){
                            Log.d("##77", it.toString())
                            Log.d("##77", it.title.toString())
                            Log.d("##77", it.text.toString())
                            it.subCondolence?.forEach { s ->
                                Log.d("##77", s.text.toString())
                            }
                            it.paragraph?.forEach { p ->
                                Log.d("##77", p.text.text.toString())
                                p.subCondolence?.forEach { ps ->
                                    Log.d("##77", ps.text.toString())
                                }
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    table.update {
                        it.copy(
                            error = result.message ?: "An unexpected error",
                            isLoading = false
                        )
                    }
                }
                is Resource.Loading -> {
                    table.update {
                        it.copy(isLoading = true)
                    }
                }
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
            }
        }
    }

    private fun getBillDetail(billId: Int){
        viewModelScope.launch {
            getBillDetailUseCase(billId).collect { result ->
                when(result){
                    is Resource.Success -> {
                        detailState.update {
                            it.copy(billDetail = result.data!!, isLoading = false)
                        }

                    }
                    is Resource.Error -> {
                        detailState.update {
                            it.copy(error = result.message ?: "An unexpected error", isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        detailState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.NetworkConnectionError -> {
                        UserState.network = false
                    }
                }
            }
        }
    }

    fun addScrap(bill: Int){

        viewModelScope.launch {
            postAddScrapUseCase(bill).collect() { it ->
                when (it) {
                    is Resource.Success -> {
                        Log.d("스크랩", "스크랩 성공")
                        detailState.update {
                            it.copy(billDetail = it.billDetail.copy(isScrapped = true))
                        }
                    }
                    is Resource.Error -> Log.d("스크랩", "스크랩 실패")
                    is Resource.Loading -> Log.d("스크랩", "스크랩 올리는중")
                }
            }
        }

    }

    fun deleteScrap(bill: Int) {
        viewModelScope.launch {
            postDeleteScrapUseCase(bill).collect() { it ->
                when (it) {
                    is Resource.Success -> {
                        Log.d("스크랩", "스크랩 취소 성공")
                        detailState.update {
                            it.copy(billDetail = it.billDetail.copy(isScrapped = false))
                        }
                    }
                    is Resource.Error -> Log.d("스크랩", "스크랩 취소 실패")
                    is Resource.Loading -> Log.d("스크랩", "스크랩 취소 올리는중")
                }
            }
        }
    }
}