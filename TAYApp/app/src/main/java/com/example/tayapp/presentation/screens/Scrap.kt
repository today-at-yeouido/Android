package com.example.tayapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.KeyLine


@Composable
fun Scrap(
    list: List<String> = listOf("1", "2", "3")
) {
    Column() {
        TayTopAppBar("스크랩")
        LazyColumn(
            modifier = Modifier.padding(horizontal = KeyLine, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(if(list.count() == 0) 10.dp else 16.dp)
        ){
            if(list.count() == 0){
                item { Title(string = "추천 법안") }
                item { CardBillWithScrap() }
                item { CardBillWithScrap() }
            } else {
                item { CardSearch() }
                item { CardSearch(list = listOf("as")) }
                item { CardSearch(list = listOf("as","afsd")) }
                item { CardSearch() }

            }
        }
    }
}
