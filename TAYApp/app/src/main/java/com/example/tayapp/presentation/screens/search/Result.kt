package com.example.tayapp.presentation.screens.search

import androidx.compose.foundation.layout.*
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
import com.example.tayapp.presentation.ui.theme.KeyLine

@Composable
fun SearchResults(
    searchResult: List<BillDto>,
    onBillClick: (Int) -> Unit
){
    Column(modifier = Modifier.fillMaxSize().padding(KeyLine)) {
        Title(
            string = "검색 결과",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
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