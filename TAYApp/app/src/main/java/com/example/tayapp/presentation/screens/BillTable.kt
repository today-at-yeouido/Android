package com.example.tayapp.presentation.screens

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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.domain.use_case.Article
import com.example.tayapp.domain.use_case.SubCondolence
import com.example.tayapp.domain.use_case.TextRow
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

    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        TayTopAppBarWithBack("개정 내용", upPress = upPress)
        LazyColumn {

            item {
                TableHeader(detailState)
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.padding(horizontal = KeyLine)) {
                    RevisionSection(billTable = table.billTable!!.condolences)
                    Spacer(modifier = Modifier.height(20.dp))
                    Title(string = "개정 내용")
                    Spacer(Modifier.height(15.dp))
                }
            }

            items(table.billTable!!.condolences) { condolences ->

                val inlinePill = mutableMapOf<String, InlineTextContent>()

                condolences.type.forEachIndexed { idx, text ->

                    /** ClausePill을 Text로 넣기 위해서 InlineContent를 inlinePill에 넣는다 */
                    inlinePill["${idx}Row"] = InlineTextContent(
                        Placeholder(60.textDp, 16.textDp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        ClausePill(clause = text)
                    }
                }

                Column(
                    modifier = Modifier.padding(start = KeyLine, end = KeyLine, bottom = 50.dp),
                ) {
                    /** 편, 장, 절, 관, 조 이름을 얻는다. */
                    val title = getRevisionTitle(
                        article = condolences,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = TayAppTheme.colors.headText,
                        inlineTextContent = inlinePill.keys as Set<String>
                    )

                    Text(
                        text = title,
                        inlineContent = inlinePill,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = TayAppTheme.colors.border)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (condolences is Article) {
                        TayButton(
                            onClick = { /*TODO*/ },
                            backgroundColor = TayAppTheme.colors.background,
                            contentColor = TayAppTheme.colors.headText,
                            border = BorderStroke(1.dp, TayAppTheme.colors.border)
                        ) {
                            Text("개정안만 보기")
                        }
                        Spacer(modifier = Modifier.height(7.dp))
                        /** 조 본문 */
                        val articleText = buildAnnotatedString { getAnnotatedString(condolences.text) }
                        if (articleText.isNotBlank()) Text(articleText)
                        SubCondolenceText(condolences.subCondolence)
                        SubCondolenceText(condolences.paragraph)
                    }
                }
            }
        }
    }
}

/** 항, 호, 목 등등 세부 subCondolence를 재귀를 통해 보여줌 */
@Composable
private fun SubCondolenceText(subCondolenceList: List<SubCondolence>?) {
    if (subCondolenceList == null) return
    Spacer(modifier = Modifier.height(23.dp))
    subCondolenceList.forEach {
        val text = buildAnnotatedString { getAnnotatedString(it = it.text) }
        Text(text = text, lineHeight = 1.65.em)
        SubCondolenceText(it.subCondolence)
    }
}


@Composable
private fun AnnotatedString.Builder.getAnnotatedString(it: TextRow) {
    var temp = it.text
    withStyle(
        SpanStyle(
            color = TayAppTheme.colors.headText, fontSize = 14.textDp,
            fontWeight = FontWeight.Light
        ),
    ) {
        if (it.row.isNotEmpty()) {
            it.row.forEach { row ->
                when (row.type) {
                    "신설" -> {
                        append(row.cText)
                        append(row.text)
                        temp = ""
                    }
                    "삭제" -> {
                        withStyle(
                            SpanStyle(
                                textDecoration = TextDecoration.LineThrough,
                                color = TayAppTheme.colors.fieldBorder,
                                fontWeight = FontWeight.Light
                            )
                        ) {
                            append(row.cText)
                        }
                        append(row.text)
                        temp = ""
                    }
                    else -> {
                        val beforeText = temp.substringBefore(row.text)
                        temp = temp.substringAfter(row.text)
                        append(beforeText)
                        withStyle(
                            SpanStyle(
                                textDecoration = TextDecoration.LineThrough,
                                color = TayAppTheme.colors.fieldBorder,
                                fontWeight = FontWeight.Light
                            )
                        ) {
                            append(row.cText)
                        }
                        append(row.text)
                    }
                }
            }
            append(temp)
        } else append(temp)
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