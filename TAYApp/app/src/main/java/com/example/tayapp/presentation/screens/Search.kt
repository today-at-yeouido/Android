package com.example.tayapp.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.CloseButton
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.presentation.viewmodels.SearchViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Search(
    onBillSelected: (Int) -> Unit
) {

    val viewModel = hiltViewModel<SearchViewModel>()
    val searchTerm by viewModel.recentTerm.collectAsState(initial = "")

    Column() {
        TayTopAppBarSearch(saveQuery = viewModel::saveRecentTerm)
        LazyColumn(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = KeyLine),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            item {
                Title(
                    string = "최근 검색어",
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                if (searchTerm.isNotEmpty()) {
                    searchTerm.split(" ").forEachIndexed { index, item ->
                        SearchHistory(string = item) { viewModel.removeRecentTerm(index) }
                    }
                }
            }
            item {
                Title(
                    string = "추천 검색어",
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                SearchTopic(list = datalist, onTagClick = {})

            }

            item {
                Title(
                    string = "최근에 본 법안",
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CardBillWithEmoij(onClick = onBillSelected)
                    CardBillWithEmoij(onClick = onBillSelected)
                    CardBillWithEmoij(onClick = onBillSelected)
                }

                Spacer(modifier = Modifier.height(20.dp))
                TayButton(
                    onClick = { /*TODO*/ },
                    backgroundColor = lm_gray000,
                    contentColor = lm_gray800,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, TayAppTheme.colors.border)
                ) {
                    Text("더보기", style = TayAppTheme.typo.typography.button)
                }

            }

        }
    }
}

@Composable
fun SearchHistory(string: String, removeHistory: () -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(26.dp),
            imageVector = TayIcons.history,
            contentDescription = null,
            tint = lm_gray300
        )
        Text(
            text = "$string",
            maxLines = 1,
            fontWeight = FontWeight.Normal,
            color = lm_gray700,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        CloseButton(
            onClick = removeHistory,
            tint = lm_gray300
        )
    }
}

@Composable
fun SearchTopic(
    list: List<String>,
    modifier: Modifier = Modifier,
    onTagClick: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier,
        mainAxisSpacing = 7.dp,
        crossAxisSpacing = 7.dp
    ) {
        list.forEach { it ->
            TayTag(it, false, onClick = onTagClick)
        }

    }
}