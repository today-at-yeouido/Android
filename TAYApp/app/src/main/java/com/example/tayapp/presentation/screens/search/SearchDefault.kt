package com.example.tayapp.presentation.screens.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.CloseButton
import com.example.tayapp.presentation.utils.TayIcons
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun SearchDefault(
    onBillSelected: (Int) -> Unit,
    searchTerm: String,
    recentViewed: List<BillDto>,
    recommendSearchTerm: List<String>,
    removeRecentTerm: (Int) -> Unit,
    onChangeQuery: (String) -> Unit,
    onSearchClick: () -> Unit,
    saveQuery: () -> Unit,
    getRecentViewedBill: () -> Unit
) {

    LaunchedEffect(key1 = true){
        getRecentViewedBill()
    }

    LazyColumn(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = KeyLine),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        item {
            Title(
                string = "최근 검색어",
                modifier = Modifier.padding(bottom = 10.dp)
            )
            if (searchTerm.isNotEmpty()) {
                searchTerm.split("&").forEachIndexed { index, item ->
                    if (index < 3) {
                        SearchHistory(
                            string = item,
                            removeHistory = { removeRecentTerm(index) },
                            onHistoryClick = { onChangeQuery(item); onSearchClick(); saveQuery() }
                        )
                    }
                }
            }
        }

        item{
            TayDivider(modifier = Modifier.padding(15.dp))
        }



        item {
            Title(
                string = "추천 검색어",
                modifier = Modifier.padding(bottom = 10.dp)
            )
            FlowRow(
                mainAxisSpacing = 7.dp,
                crossAxisSpacing = 7.dp
            ) {
                recommendSearchTerm.forEach { item ->
                    TayTag(
                        item,
                        false,
                        onClick = { onChangeQuery(item); onSearchClick(); saveQuery() })
                }
            }
        }

        item{
            TayDivider()
        }

        
        item {
            Title(
                string = "최근에 본 법안",
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        items(recentViewed){ it ->
            CardBillWithEmoji(
                bill = it.toDomain(),
                onClick = onBillSelected
            )
        }

//        item{
//            Spacer(modifier = Modifier.height(20.dp))
//            TayButton(
//                onClick = { /*TODO*/ },
//                backgroundColor = TayAppTheme.colors.background,
//                contentColor = TayAppTheme.colors.headText,
//                modifier = Modifier.fillMaxWidth(),
//                border = BorderStroke(1.dp, TayAppTheme.colors.border)
//            ) {
//                Text("더보기", style = TayAppTheme.typo.typography.button)
//            }
//
//        }
    }
}

@Composable
fun SearchHistory(
    string: String,
    removeHistory: () -> Unit,
    onHistoryClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .clickable(onClick = onHistoryClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(26.dp),
            imageVector = TayIcons.history,
            contentDescription = null,
            tint = TayAppTheme.colors.disableIcon
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "$string",
            maxLines = 1,
            fontWeight = FontWeight.Normal,
            color = TayAppTheme.colors.bodyText,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        CloseButton(
            onClick = removeHistory,
            tint = TayAppTheme.colors.disableIcon
        )
    }
}
