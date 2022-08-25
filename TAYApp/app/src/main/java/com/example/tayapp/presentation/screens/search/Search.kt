package com.example.tayapp.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.TayTopAppBarSearch
import com.example.tayapp.presentation.screens.NetworkErrorScreen
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.viewmodels.SearchViewModel


@Composable
fun Search(
    onBillSelected: (Int) -> Unit
) {

    val viewModel = hiltViewModel<SearchViewModel>()
    val searchState by viewModel.searchState.collectAsState()

    if (UserState.network) {
        Column {
            TayTopAppBarSearch(
                saveQuery = viewModel::saveRecentTerm,
                onSearchClick = viewModel::getSearchResult,
                onCloseClick = viewModel::onClearQuery,
                onChangeQuery = viewModel::onChangeQuery,
                autoComplete = searchState.autoComplete,
                queryValue = searchState.query
            )

            if (!searchState.searching) {
                SearchDefault(
                    onBillSelected = onBillSelected,
                    searchTerm = searchState.recentTerm,
                    recentViewed = searchState.recentViewedBill,
                    recommendSearchTerm = searchState.recommendSearch,
                    removeRecentTerm = viewModel::removeRecentTerm,
                    onChangeQuery = viewModel::onChangeQuery,
                    onSearchClick = viewModel::getSearchResult,
                    saveQuery = viewModel::saveRecentTerm
                )
            } else if (searchState.searching && searchState.bill.isEmpty()) {
                NoResult()
            } else {
                SearchResults(
                    searchResult = searchState,
                    onBillClick = onBillSelected,
                    viewModel::getPagingResult,
                    keyword = searchState.keyword
                )
            }
        }
    } else {
        NetworkErrorScreen {
            UserState.network = true
        }
    }
}