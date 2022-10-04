package com.example.tayapp.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.example.tayapp.presentation.components.TayTopAppBarSearch
import com.example.tayapp.presentation.navigation.GroupBillParcelableModel
import com.example.tayapp.presentation.screens.NetworkErrorScreen
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.utils.addFocusCleaner
import com.example.tayapp.presentation.viewmodels.SearchViewModel


@Composable
fun Search(
    onBillSelected: (Int) -> Unit,
    onGroupBillSelected: (Int) -> Unit
) {

    val viewModel = hiltViewModel<SearchViewModel>()
    val searchState by viewModel.searchState.collectAsState()



    if (UserState.network) {
        Column(
            modifier = Modifier.addFocusCleaner(LocalFocusManager.current)
        ) {
            TayTopAppBarSearch(
                saveQuery = viewModel::saveRecentTerm,
                onSearchClick = viewModel::getSearchResult,
                onCloseClick = viewModel::onClearQuery,
                onChangeQuery = viewModel::onChangeQuery,
                autoComplete = searchState.autoComplete,
                queryValue = searchState.query,
                isFocused = searchState.isFocused,
                onFocusChange = viewModel::onFocusChange,
                onAutoCompleteClick = viewModel::autoCompleteClick,
                searching = searchState.searching
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
                    saveQuery = viewModel::saveRecentTerm,
                    getRecentViewedBill = viewModel::getRecentViewedBill
                )
            } else if (searchState.searching && searchState.bill.isEmpty()) {
                NoResult()
            } else {
                SearchResults(
                    searchResult = searchState,
                    onBillClick = onBillSelected,
                    viewModel::getPagingResult,
                    keyword = searchState.keyword,
                    onGroupBillSelected = onGroupBillSelected
                )
            }
        }
    } else {
        NetworkErrorScreen {
            UserState.network = true
        }
    }
}