package com.example.tayapp.presentation.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.bill.SearchBillDto
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.presentation.components.CardSearch
import com.example.tayapp.presentation.components.Title

@Composable
fun SearchResults(
    searchResult: List<BillDto>,
    onBillClick: (Int) -> Unit
){
    Column() {
        Title(
            string = "검색 결과",
            modifier = Modifier.padding(bottom = 10.dp)
        )

        LazyColumn(){
            items(searchResult){ it->
                CardSearch(
                    bill = it.toDomain(),
                    onBillSelected = onBillClick
                )
            }
        }
    }
}

@Composable
fun NoResult(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Title(
            string = "아무것도 없습니당",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.Center)
        )
    }
}