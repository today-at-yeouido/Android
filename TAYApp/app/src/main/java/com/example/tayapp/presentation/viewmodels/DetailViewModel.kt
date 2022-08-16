package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.RecentSearchTermUseCase
import com.example.tayapp.domain.use_case.scrap.PostAddScrapUseCase
import com.example.tayapp.domain.use_case.scrap.PostDeleteScrapUseCase
import com.example.tayapp.domain.use_case.search.GetSearchResultUseCase
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postAddScrapUseCase: PostAddScrapUseCase,
    private val postDeleteScrapUseCase: PostDeleteScrapUseCase
) : ViewModel()  {

    fun AddScrap(bill: Int){
        viewModelScope.launch {
            postAddScrapUseCase(bill).collect() { it ->
                when (it) {
                    is Resource.Success -> Log.d("스크랩", "스크랩 성공")
                    is Resource.Error -> Log.d("스크랩", "스크랩 실패")
                    is Resource.Loading -> Log.d("스크랩", "스크랩 올리는중")
                }
            }
        }
    }
}