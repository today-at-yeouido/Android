package com.example.tayapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.domain.use_case.Article
import com.example.tayapp.domain.use_case.SubCondolence
import com.example.tayapp.domain.use_case.TextRow
import com.example.tayapp.presentation.components.*
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

                var articleRevision = ""

                condolences.type.forEachIndexed { idx, text ->
                    /** ClausePill을 Text로 넣기 위해서 InlineContent를 inlinePill에 넣는다 */
                    inlinePill["${idx}Row"] = InlineTextContent(
                        Placeholder(60.textDp, 16.textDp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        ClausePill(clause = text)
                    }
                    if (text == "신설" || text == "삭제") articleRevision = text
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
                            .background(color = TayAppTheme.colors.border),
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (condolences is Article) {
                        if (condolences.type.contains("일부 수정")) {
                            TayToggleButton(
                                onClick = {
                                    condolences.showNormal.value = !condolences.showNormal.value
                                },
                                backgroundColor = TayAppTheme.colors.background,
                                contentColor = TayAppTheme.colors.headText,
                                border = BorderStroke(1.dp, TayAppTheme.colors.border),
                                clickedContent = {Text("현행안과 함께 보기", fontWeight = FontWeight.Medium)},
                                notClickedContent = {Text("개정안만 보기", fontWeight = FontWeight.Medium)}
                            )
                        }
                        Spacer(modifier = Modifier.height(7.dp))

                        /** 조 본문 */
                        val articleText =
                            buildAnnotatedString { GetAnnotatedString(condolences.text) }

                        if (articleText.isNotBlank()) Text(articleText)

                        SubCondolenceText(
                            condolences.subCondolence,
                            padding = 16.dp,
                            articleRevision = articleRevision,
                            showType = condolences.showNormal.value
                        )
                        SubCondolenceText(
                            condolences.paragraph,
                            isParagraph = true,
                            articleRevision = articleRevision,
                            showType = condolences.showNormal.value
                        )
                    }
                }
            }
        }
    }
}


/** 항, 호, 목 등등 세부 subCondolence를 재귀를 통해 보여줌 */
@Composable
private fun SubCondolenceText(
    subCondolenceList: List<SubCondolence>?,
    padding: Dp = 0.dp,
    isParagraph: Boolean = false,
    articleRevision: String = "",
    showType: Boolean = true
) {
    val inlinePill = mapOf("신설" to InlineTextContent(
        Placeholder(34.sp, 14.sp, PlaceholderVerticalAlign.TextCenter)
    ) { ClausePill(clause = "신설") })

    if (subCondolenceList == null) return
    subCondolenceList.forEach { sub ->
        val text = buildAnnotatedString {
            GetAnnotatedString(
                text = sub.text,
                isParagraph = isParagraph,
                articleRevision = articleRevision,
                showType = showType
            )
        }
        Text(
            text = text,
            lineHeight = 1.65.em,
            letterSpacing = (-0.2).sp,
            inlineContent = inlinePill,
            modifier = Modifier.padding(horizontal = padding, vertical = 1.dp)
        )
        if (sub.text.table.isNotEmpty()) {
            Log.d("##12", "table")
            Log.d("##12", "${sub.text.table}")
            sub.text.table.forEach {
                TayTable(it)
            }
        }

        if (padding == 0.dp) Spacer(modifier = Modifier.height(23.dp))
        SubCondolenceText(
            subCondolenceList = sub.subCondolence,
            padding = padding + 13.dp,
            articleRevision = articleRevision,
            showType = showType
        )
    }
    if (!isParagraph) Spacer(modifier = Modifier.height(23.dp))
}

@Composable
private fun AnnotatedString.Builder.GetAnnotatedString(
    text: TextRow,
    isParagraph: Boolean = false,
    articleRevision: String,
    showType: Boolean
) {
    if (articleRevision.isBlank()) {
        GetAnnotatedString(text = text, isParagraph = isParagraph, showType = showType)
    } else {
        GetAnnotatedString(text = text, articleRevision = articleRevision)
    }
}

@Composable
private fun AnnotatedString.Builder.GetAnnotatedString(
    text: TextRow,
    articleRevision: String
) {
    if (articleRevision == "신설") {
        withStyle(
            SpanStyle(
                color = TayAppTheme.colors.headText, fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append(text.text)
        }
    } else {
        append("<삭 제>")
        withStyle(
            SpanStyle(
                textDecoration = TextDecoration.LineThrough,
                color = TayAppTheme.colors.fieldBorder,
            )
        ) {
            append(text.text)
        }
    }
}

@Composable
private fun AnnotatedString.Builder.GetAnnotatedString(
    text: TextRow,
    isParagraph: Boolean = false,
    showType: Boolean = true
) {
    var temp = text.text
    withStyle(
        SpanStyle(
            color = TayAppTheme.colors.headText, fontSize = 14.sp,
            fontWeight = FontWeight.Light
        ),
    ) {
        if (text.row.isNotEmpty()) {
            text.row.forEach { row ->
                when (row.type) {
                    "신설" -> {
                        if (isParagraph) {
                            appendInlineContent("신설")
                        } else append("${row.cText} ")
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append(row.text)
                            temp = ""
                        }
                    }
                    "삭제" -> {
                        append(row.text)
                        withStyle(
                            SpanStyle(
                                textDecoration = TextDecoration.LineThrough,
                                color = TayAppTheme.colors.fieldBorder,
                            )
                        ) {
                            append(row.cText)
                        }
                        temp = ""
                    }
                    else -> {
                        if (showType) {
                            /**4818 같이 일부 수정인데 항,호 같이 있는 경우  */
                            if (temp.contains(row.text)) {
                                val beforeText = temp.substringBefore(row.text)
                                temp = temp.substringAfter(row.text)
                                append(beforeText)
                                withStyle(
                                    SpanStyle(
                                        textDecoration = TextDecoration.LineThrough,
                                        color = TayAppTheme.colors.fieldBorder,
                                    )
                                ) {
                                    append(row.cText)
                                }
                                withStyle(
                                    SpanStyle(
                                        background = TayAppTheme.colors.information1
                                    )
                                ) {
                                    append(row.text)
                                }
                            } else {
                                withStyle(
                                    SpanStyle(
                                        textDecoration = TextDecoration.LineThrough,
                                        color = TayAppTheme.colors.fieldBorder,
                                    )
                                ) {
                                    append(row.cText)
                                }
                                withStyle(
                                    SpanStyle(
                                        background = TayAppTheme.colors.information1
                                    )
                                ) {
                                    append(temp)
                                }
                                temp = ""
                            }
                        } else {
                            append(temp)
                            temp = ""
                        }
                    }
                }
            }
            // 마지막 부분 textRow 출력
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