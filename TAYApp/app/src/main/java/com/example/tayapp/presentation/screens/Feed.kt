package com.example.tayapp.presentation.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.viewmodels.HomeViewModel


@Composable
fun Feed(modifier: Modifier = Modifier, navController: NavController) {

    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<HomeViewModel>()
    val mostViewed by viewModel.state.collectAsState()
    val userItems = listOf<Int>(1, 2, 3)

    Column {
        CardMostViewed(items = mostViewed.bill)

        Column() {
            TayHomeTopAppBar(modifier = Modifier)
            LazyColumn {
                item {
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





