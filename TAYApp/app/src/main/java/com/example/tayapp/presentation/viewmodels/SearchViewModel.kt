package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.RecentSearchTermUseCase
import com.example.tayapp.domain.use_case.search.GetSearchResultUseCase
import com.example.tayapp.presentation.states.FeedUiState
import com.example.tayapp.presentation.states.SearchState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchResultUseCase,
    private val getRecentSearchTermUseCase: RecentSearchTermUseCase
) :
    ViewModel() {

    var searchState = MutableStateFlow(SearchState(isLoading = true, searching = false))
        private set

    init {
        getRecentTerm()
    }

    fun getSearchResult(){
        getSearchUseCase(searchState.value.query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    searchState.update {
                        it.copy(bill = result.data ?: emptyList(), isLoading = false, searching = true)
                    }

                }
                is Resource.Error -> {
                    searchState.update {
                        it.copy(error = result.message ?: "An unexpected error", isLoading = false, searching = true)
                    }
                }
                is Resource.Loading -> {
                    searchState.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }.launchIn(viewModelScope)

        searchState.update {
            it.copy(keyword = searchState.value.query)
        }
    }


    fun onClearQuery(){
        searchState.update {
            it.copy(bill = emptyList(), searching = false, query = "", keyword = "")
        }
    }

    fun onChangeQuery(query: String){
        searchState.update {
            it.copy(query = query)
        }
    }


    fun getRecentTerm(){
        viewModelScope.launch {
            val result = getRecentSearchTermUseCase.getRecentSearchUseCase()
            searchState.update {
                it.copy(recentTerm = result.first(), isLoading = false )
            }
        }
    }



    fun saveRecentTerm() {
        viewModelScope.launch {
            val result = searchState.value.recentTerm
            getRecentSearchTermUseCase.saveRecentSearchUseCase("${searchState.value.query} $result".trim())
            searchState.update {
                it.copy(recentTerm = getRecentSearchTermUseCase.getRecentSearchUseCase().first(), isLoading = false )
            }
        }
    }

    fun removeRecentTerm(index: Int) {
        viewModelScope.launch {
            val list = searchState.value.recentTerm.split(" ").toMutableList()
            list.removeAt(index)
            getRecentSearchTermUseCase.removeRecentSearchUseCase(list.toString())
            searchState.update {
                it.copy(recentTerm = getRecentSearchTermUseCase.getRecentSearchUseCase().first(), isLoading = false )
            }
        }
    }
}
