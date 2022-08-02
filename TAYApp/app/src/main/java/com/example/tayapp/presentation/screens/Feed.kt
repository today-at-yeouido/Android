package com.example.tayapp.presentation.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.KeyLine


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

    Column() {
        TayHomeTopAppBar(modifier = Modifier)
        LazyColumn{
            item{
                //CardMostViewed(items = items)
                Spacer(modifier = Modifier.height(40.dp))

                Title(
                    "사용자 맞춤 추천법안",
                    modifier = Modifier
                        .padding(vertical = 0.dp, horizontal = KeyLine)
                    )
                CardsUser()
                Spacer(modifier = Modifier.height(40.dp))

                Title(
                    "최근 발의 법안",
                    modifier = Modifier
                        .padding(vertical = 7.dp, horizontal = KeyLine)
                )
                userItems.forEach {
                    CardBill(
                        modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                    )
                    }
                }
            }
        }

    }
}





