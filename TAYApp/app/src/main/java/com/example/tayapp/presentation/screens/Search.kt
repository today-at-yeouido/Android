package com.example.tayapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tayapp.presentation.components.TayTopAppBarSearch

@Composable
fun Search(modifier: Modifier) {
    Column() {
        TayTopAppBarSearch(onQueryChange = {}, onSearchFocusChange = {})
        LazyColumn{

        }
    }
}