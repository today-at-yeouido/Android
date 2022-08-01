package com.example.tayapp.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.KeyLine


@Composable
fun Feed(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val items = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
    val userItems = listOf<Int>(1, 2, 3)

    Column() {
        TayHomeTopAppBar(modifier = Modifier)
        LazyColumn{
            item{
                CardMostViewed(items = items)
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
                items.forEach {
                    CardBill(
                        modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                    )
                }
            }
        }

    }
}





