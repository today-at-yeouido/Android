package com.example.tayapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.GetAutoCompleteUseCase
import com.example.tayapp.domain.use_case.GetRecentViewedBillUseCase
import com.example.tayapp.domain.use_case.RecentSearchTermUseCase
import com.example.tayapp.domain.use_case.search.GetSearchResultUseCase
import com.example.tayapp.presentation.states.SearchState
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchResultUseCase,
    private val getRecentSearchTermUseCase: RecentSearchTermUseCase,
    private val getAutoCompleteUseCase: GetAutoCompleteUseCase,
    private val getRecentViewedBillUseCase: GetRecentViewedBillUseCase
) :
    ViewModel() {

    var searchState = MutableStateFlow(SearchState(isLoading = true, searching = false))
        private set

    init {
        getRecentTerm()
        getRecentViewedBill()
    }


    fun getRecentViewedBill(){
        getRecentViewedBillUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    searchState.update {
                        it.copy(
                            recentViewedBill = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                }
                is Resource.Error -> {
                    searchState.update {
                        it.copy(
                            error = result.message ?: "An unexpected error",
                            isLoading = false,
                            searching = true
                        )
                    }
                }
                is Resource.Loading -> {
                    searchState.update {
                        it.copy(isLoading = true)
                    }
                }
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSearchResult() {
        getSearchUseCase(searchState.value.query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    searchState.update {
                        it.copy(
                            bill = result.data ?: emptyList(),
                            isLoading = false,
                            searching = true
                        )
                    }

                }
                is Resource.Error -> {
                    searchState.update {
                        it.copy(
                            error = result.message ?: "An unexpected error",
                            isLoading = false,
                            searching = true
                        )
                    }
                }
                is Resource.Loading -> {
                    searchState.update {
                        it.copy(isLoading = true)
                    }
                }
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
            }
        }.launchIn(viewModelScope)

        searchState.update {
            it.copy(keyword = searchState.value.query)
        }
    }


    fun onClearQuery() {
        searchState.update {
            it.copy(bill = emptyList(), searching = false, query = "", keyword = "", autoComplete = emptyList())
        }
    }

    fun onChangeQuery(query: String) {
        searchState.update {
            it.copy(query = query)
        }
        getAutoComplete()
    }


    private fun getRecentTerm() {
        viewModelScope.launch {
            val result = getRecentSearchTermUseCase.getRecentSearchUseCase()
            searchState.update {
                it.copy(recentTerm = result.first(), isLoading = false)
            }
        }
    }


    fun saveRecentTerm() {
        viewModelScope.launch {
            val recentTerm = searchState.value.recentTerm
            val query = searchState.value.query
            getRecentSearchTermUseCase.saveRecentSearchUseCase(recentTerm, query)
            searchState.update {
                it.copy(
                    recentTerm = getRecentSearchTermUseCase.getRecentSearchUseCase().first(),
                    isLoading = false
                )
            }
        }
    }

    fun removeRecentTerm(index: Int) {
        viewModelScope.launch {
            val recentTerm = searchState.value.recentTerm
            getRecentSearchTermUseCase.saveRecentSearchUseCase(recentTerm, index)
            searchState.update {
                it.copy(
                    recentTerm = getRecentSearchTermUseCase.getRecentSearchUseCase().first(),
                    isLoading = false
                )
            }
        }
    }

    fun getAutoComplete(){
        getAutoCompleteUseCase(searchState.value.query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if(!result.data.isNullOrEmpty()){
                        searchState.update {
                            it.copy(
                                autoComplete = result.data!!
                            )
                        }
                    }else{
                        searchState.update {
                            it.copy(
                                autoComplete = emptyList()
                            )
                        }
                    }

                }
                is Resource.Error -> {
                    searchState.update {
                        it.copy(
                            error = result.message ?: "An unexpected error"
                        )
                    }
                }
                is Resource.Loading -> {
                    searchState.update {
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
