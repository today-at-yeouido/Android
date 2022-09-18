package com.example.tayapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.ClausePill
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.components.Title
import com.example.tayapp.presentation.states.BillDetailUiState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.viewmodels.DetailViewModel
import com.example.tayapp.utils.textDp

@Composable
fun BillTable(
    viewModel: DetailViewModel = hiltViewModel(), upPress: () -> Unit
) {
    val detailState by viewModel.detailState.collectAsState()

    val table by viewModel.table.collectAsState()


    /**
     * 1. 조 삭제, 조 신설
     * 2. 일부 신설, 일부 삭제
     * 3.
     * */
    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        TayTopAppBarWithBack("개정 내용", upPress = upPress)
        LazyColumn() {

            item {
                TableHeader(detailState)
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.padding(horizontal = KeyLine)) {
                    RevisionSection(billTable = table.billTable!!.article)
                    Spacer(modifier = Modifier.height(20.dp))
                    Title(string = "개정 내용")
                    Spacer(Modifier.height(15.dp))
                }
            }

            items(table.billTable!!.article) { article ->

                val inlinePill = mutableMapOf<String, InlineTextContent>()

                article.type.forEachIndexed { idx, text ->
                    inlinePill["${idx}Row"] = InlineTextContent(
                        Placeholder(60.textDp, 16.textDp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        ClausePill(clause = text)
                    }
                }

                val title = getRevisionTitle(
                    article = article,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TayAppTheme.colors.headText,
                    inlineTextContent = inlinePill.keys as Set<String>
                )

                val articleText = buildAnnotatedString {
                    article.text.let {
                        var text = article.title.text
                        if (it.row.isNotEmpty()) {
                            it.row.forEach { row ->
                                val beforeText = text.substringBefore(row.text)
                                text = text.substringAfter(row.text)
                                append(beforeText)
                                withStyle(
                                    SpanStyle(
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                ) {
                                    append(row.cText)
                                }
                                append(row.text)
                            }
                        } else append(text)
                    }
                }
                Column(
                    modifier = Modifier.padding(start = KeyLine, end = KeyLine, bottom = 50.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = title,
                        inlineContent = inlinePill,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = TayAppTheme.colors.border)
                    )
                    TayButton(
                        onClick = { /*TODO*/ },
                        backgroundColor = TayAppTheme.colors.background,
                        contentColor = TayAppTheme.colors.headText,
                        border = BorderStroke(1.dp, TayAppTheme.colors.border)
                    ) {
                        Text("개정안만 보기")
                    }
                    article.paragraph?.forEach { q ->
                        val text = buildAnnotatedString {
                            var temp = q.textRow.text
                            if (q.textRow.row.isNotEmpty()) {
                                q.textRow.row.forEach { row ->
                                    val beforeText = temp.substringBefore(row.text)
                                    temp = temp.substringAfter(row.text)
                                    append(beforeText)
                                    withStyle(
                                        SpanStyle(
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    ) {
                                        append(row.cText)
                                    }
                                    append(row.text)
                                }
                                append(temp)
                            } else append(temp)
                        }
                        Log.d("##88", "subparahraph $text")
                        Text(text = text)
                        q.subParagraph?.forEach { s ->
                            val text2 = buildAnnotatedString {
                                var temp = s.textRow.text
                                if (s.textRow.row.isNotEmpty()) {
                                    s.textRow.row.forEach { row ->
                                        val beforeText = temp.substringBefore(row.text)
                                        temp = temp.substringAfter(row.text)
                                        append(beforeText)
                                        withStyle(
                                            SpanStyle(
                                                textDecoration = TextDecoration.LineThrough
                                            )
                                        ) {
                                            append(row.cText)
                                        }
                                        append(row.text)
                                    }
                                    append(temp)
                                } else append(temp)
                            }
                            Log.d("##88", "subparahraph $text2")
                            Text(text2)
                            s.subParagraph?.forEach { item ->
                                val text2 = buildAnnotatedString {
                                    var temp = item.textRow.text
                                    if (item.textRow.row.isNotEmpty()) {
                                        item.textRow.row.forEach { row ->
                                            val beforeText = temp.substringBefore(row.text)
                                            temp = temp.substringAfter(row.text)
                                            append(beforeText)
                                            withStyle(
                                                SpanStyle(
                                                    textDecoration = TextDecoration.LineThrough
                                                )
                                            ) {
                                                append(row.cText)
                                            }
                                            append(row.text)
                                        }
                                        append(temp)
                                    } else append(temp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun TableHeader(detailState: BillDetailUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                TayAppTheme.colors.success2,
                RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
            )
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        DetailTitle(bill = detailState.billDetail)
    }
}