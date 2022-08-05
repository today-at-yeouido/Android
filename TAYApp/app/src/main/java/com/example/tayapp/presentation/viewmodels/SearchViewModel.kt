package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.RecentSearchTermUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: RecentSearchTermUseCase) :
    ViewModel() {

    var recentTerm = useCase.getRecentSearchUseCase()
        private set

    fun saveRecentTerm(query: String) {
        viewModelScope.launch {
            val result = recentTerm.first()
            useCase.saveRecentSearchUseCase("$query $result".trim())
        }
    }

    fun removeRecentTerm(index: Int) {
        viewModelScope.launch {
            val list = recentTerm.first().split(" ").toMutableList()
            list.removeAt(index)
            useCase.removeRecentSearchUseCase(list.toString())
        }
    }
}
