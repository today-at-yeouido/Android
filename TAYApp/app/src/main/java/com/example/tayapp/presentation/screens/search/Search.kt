package com.example.tayapp.presentation.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.viewmodels.SearchViewModel


@Composable
fun Search(
    onBillSelected: (Int) -> Unit
) {

    val viewModel = hiltViewModel<SearchViewModel>()
    val searchState by viewModel.searchState.collectAsState()

    Column {
        TayTopAppBarSearch(
            saveQuery = viewModel::saveRecentTerm,
            onSearchClick = viewModel::getSearchResult,
            onCloseClick = viewModel::onClearQuery
        )

        if(!searchState.searching){
            SearchDefault(
                onBillSelected = onBillSelected,
                searchTerm = searchState.recentTerm,
                removeRecentTerm = viewModel::removeRecentTerm
            )
        }else if(searchState.searching && searchState.bill.size == 0){
            NoResult()
        }else{
            SearchResults(searchResult = searchState.bill, onBillClick = onBillSelected)
        }
    }
}