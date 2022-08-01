package com.example.tayapp.presentation.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.tayapp.presentation.components.CardMostViewed
import com.example.tayapp.presentation.components.CardUser
import com.example.tayapp.presentation.navigation.MainDestination
import com.example.tayapp.presentation.viewmodels.HomeViewModel
import okhttp3.Route


@Composable
fun Feed(modifier: Modifier = Modifier, navController: NavController) {


    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<HomeViewModel>()
    val mostViewed by viewModel.state.collectAsState()
    val userItems = listOf<Int>(1, 2, 3)
    Column(
        modifier = Modifier.clickable { navController.navigate(MainDestination.LOGIN) }
    ) {
        CardMostViewed(items = mostViewed.bill)

        /**
         * 테스트용 수정예정
         */
        LazyRow(){
            item { CardUser(items = userItems) }
            item { CardUser(items = userItems) }
            item { CardUser(items = userItems) }
        }
    }
}





