package com.example.tayapp.presentation.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tayapp.R
import com.example.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.example.tayapp.presentation.components.CardMultiple
import com.example.tayapp.presentation.components.CardSearch
import com.example.tayapp.presentation.components.LoadingView
import com.example.tayapp.presentation.components.Title
import com.example.tayapp.presentation.navigation.GroupBillParcelableModel
import com.example.tayapp.presentation.states.SearchState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun SearchResults(
    searchResult: SearchState,
    onBillClick: (Int) -> Unit,
    loadPaging: () -> Unit,
    keyword: String,
    onGroupBillSelected: (Int, GroupBillParcelableModel) -> Unit
) {

    if (searchResult.isLoading) {
        LoadingView(modifier = Modifier.fillMaxSize())
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(KeyLine)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Title(
                        string = "검색 결과",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                itemsIndexed(items =searchResult.bill) { index, bill ->
                    if (index >= searchResult.bill.size - 1 && !searchResult.endReached && !searchResult.pagingLoading) {
                        loadPaging()
                    }
                    if (bill.bills.size == 1) {
                        CardSearch(
                            bill = bill,
                            onBillSelected = onBillClick,
                            keyword = keyword
                        )
                    } else {
                        CardMultiple(
                            bill = bill,
                            onLineClick = onBillClick,
                            keyword = keyword,
                            onButtonClick = onGroupBillSelected
                        )
                    }
                }
                item {
                    if (searchResult.pagingLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
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