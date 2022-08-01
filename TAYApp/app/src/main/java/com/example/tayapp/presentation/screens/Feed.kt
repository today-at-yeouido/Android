package com.example.tayapp.presentation.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.tayapp.presentation.components.CardMostViewed
import com.example.tayapp.presentation.components.CardUser
import com.example.tayapp.presentation.components.TayHomeTopAppBar


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

                LazyRow(){
                    item { CardUser(items = userItems) }
                    item { CardUser(items = userItems) }
                    item { CardUser(items = userItems) }
                }
            }
        }

    }
}





