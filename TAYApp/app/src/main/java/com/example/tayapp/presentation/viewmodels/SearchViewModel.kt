package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.domain.use_case.GetAutoCompleteUseCase
import com.example.tayapp.domain.use_case.GetRecentViewedBillUseCase
import com.example.tayapp.domain.use_case.RecentSearchTermUseCase
import com.example.tayapp.domain.use_case.RecommendSearchUseCase
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
    private val getRecentViewedBillUseCase: GetRecentViewedBillUseCase,
    val getRecommendSearchUseCase: RecommendSearchUseCase
) :
    ViewModel() {

    var searchState = MutableStateFlow(SearchState(isLoading = true, searching = false))
        private set

    init {
        Log.d("##66", "뷰모델 실행")
        getRecentTerm()
        getRecentViewedBill()
        getRecommendSearchTerm()
    }

    fun getRecentViewedBill() {
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

    fun getPagingResult() {
        getSearchUseCase(searchState.value.query, searchState.value.nextPage).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    searchState.update {
                        it.copy(
                            bill = it.bill + result.data as List<ScrapBillDto>,
                            pagingLoading = false,
                            searching = true,
                            nextPage = it.nextPage + 1,
                        )
                    }

                }
                is Resource.Error -> {
                    searchState.update {
                        it.copy(
                            error = result.message ?: "An unexpected error",
                            pagingLoading = false,
                            searching = true,
                            endReached = true
                        )
                    }
                }
                is Resource.Loading -> {
                    searchState.update {
                        it.copy(pagingLoading = true)
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
                            nextPage = it.nextPage,
                            isLoading = false,
                            searching = true,
                            endReached = false
                        )
                    }

                }
                is Resource.Error -> {
                    searchState.update {
                        it.copy(
                            error = result.message ?: "An unexpected error",
                            isLoading = false,
                            searching = true,
                            endReached = true
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
            it.copy(
                bill = emptyList(),
                searching = false,
                query = "",
                keyword = "",
                autoComplete = emptyList()
            )
        }
    }

    fun onFocusChange(focused: Boolean) {
        searchState.update {
            it.copy(
                isFocused = focused
            )
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

    //자동완성을 클릭했을때
    fun autoCompleteClick(query: String) {
        onChangeQuery(query)
        getSearchResult()
        saveRecentTerm()
        searchState.update {
            it.copy(
                searching = true
            )
        }
    }

    private fun getRecommendSearchTerm() {
        getRecommendSearchUseCase().onEach { result ->
            Log.d("##66", "뷰모델 실행")
            when (result) {
                is Resource.Success -> {
                    searchState.update {
                        it.copy(
                            recommendSearch = result.data!!,
                            isLoading = false
                        )
                    }
                }
                is Resource.Loading -> {
                    searchState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.NetworkConnectionError -> {
                    UserState.network = false
                }
                else -> {
                    searchState.update {
                        it.copy(
                            error = result.message!!,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAutoComplete() {
        getAutoCompleteUseCase(searchState.value.query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (!result.data.isNullOrEmpty()) {
                        searchState.update {
                            it.copy(
                                autoComplete = result.data!!,
                                isLoading = false
                            )
                        }
                    } else {
                        searchState.update {
                            it.copy(
                                autoComplete = emptyList(),
                                isLoading = false
                            )
                        }
                    }

                }
                is Resource.Error -> {
                    searchState.update {
                        it.copy(
                            error = result.message ?: "An unexpected error",
                            isLoading = false
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
