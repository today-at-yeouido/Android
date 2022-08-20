package com.example.tayapp.presentation.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.tayapp.R
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.presentation.components.CardSearch
import com.example.tayapp.presentation.components.LoadingView
import com.example.tayapp.presentation.components.Title
import com.example.tayapp.presentation.states.SearchState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun SearchResults(
    searchResult: SearchState,
    onBillClick: (Int) -> Unit,
    keyword: String
) {
    if (searchResult.isLoading) {
        LoadingView(modifier = Modifier.fillMaxSize())
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(KeyLine)) {
            Title(
                string = "검색 결과",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(searchResult.bill) {
                    CardSearch(
                        bill = it.toDomain(),
                        onBillSelected = onBillClick,
                        keyword = keyword
                    )
                }
            }
        }
    }
}

@Composable
fun NoResult() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            modifier = Modifier
                .padding(top = 138.dp)
                .size(100.dp)
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.ic_tay_ch_emoji_gray_default),
            contentDescription = "main_title_image"
        )

        Text(
            text = "아직 스크랩된 법안이 없어요.\n" +
                    "관심 있는 법안을 스크랩해보세요!",
            modifier = Modifier
                .padding(top = 258.dp)
                .align(Alignment.TopCenter),
            textAlign = TextAlign.Center,
            color = TayAppTheme.colors.disableText
        )
    }
}