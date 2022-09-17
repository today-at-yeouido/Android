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

    val billId = viewModel.billId
    val table by viewModel.table.collectAsState()
    

    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        TayTopAppBarWithBack("개정 내용", upPress = upPress)
        LazyColumn() {

            item {
                TableHeader(detailState)
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.padding(horizontal = KeyLine)) {
                    RevisionSection(billTable = table.billTable!!)
                    Spacer(modifier = Modifier.height(20.dp))
                    Title(string = "개정 내용")
                    Spacer(Modifier.height(15.dp))
                }
            }

            items(table.billTable!!) { billTable ->

                val inlinePill = mutableMapOf<String, InlineTextContent>()

                billTable.type.forEachIndexed { idx, text ->
                    inlinePill["${idx}Row"] = InlineTextContent(
                        Placeholder(60.textDp, 16.textDp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        ClausePill(clause = text)
                    }
                }

                val title = getRevisionTitle(
                    billTable = billTable,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TayAppTheme.colors.headText,
                    inlineTextContent = inlinePill.keys as Set<String>
                )

                val mainText = buildAnnotatedString {
                    if (billTable.type.contains("신설")) {
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = TayAppTheme.colors.headText
                            )
                        ) {
                            billTable.amendmentText.forEach {
                                append(it)
                            }
                        }
                    } else {
                        var textIndex = 0
                        for (c in billTable.currentText) {
                            val amendmentText =
                                if (textIndex < billTable.amendmentText.size) billTable.amendmentText[textIndex]
                                else ""
                            if (c.underline) {
                                when {
                                    amendmentText.contains("<단서 신설>")
                                            || amendmentText.contains("<신설>") -> {
                                        withStyle(
                                            SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                color = TayAppTheme.colors.headText
                                            )
                                        ) {
                                            append("\n${billTable.amendmentText[textIndex]}\n")
                                        }
                                        textIndex++
                                    }
                                    else -> {
                                        withStyle(
                                            SpanStyle(
                                                textDecoration = TextDecoration.LineThrough,
                                                color = TayAppTheme.colors.fieldBorder
                                            )
                                        ) {
                                            append(c.text)
                                        }
                                        withStyle(
                                            SpanStyle(
                                                background = TayAppTheme.colors.information1,
                                                color = TayAppTheme.colors.headText
                                            )
                                        ) {
                                            append(amendmentText)
                                            textIndex++
                                        }
                                    }

                                }
                            } else if (c.text.contains("(생  략)")) {
                                withStyle(SpanStyle(color = TayAppTheme.colors.headText)) {
                                    append(c.text.replace("(생  략)", "(현행과 같음)"))
                                }

                            } else {
                                withStyle(SpanStyle(color = TayAppTheme.colors.headText)) {
                                    append(c.text)
                                }
                            }
                        }
                    }
                }

                val regex1 = Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳].+")
                val regex2 = Regex("[1234567890]\\..+")
                val regex3 = Regex("[가나]\\..+")

                val main = mainText.replace(regex1) { "\n" + it.value }.replace(regex2) { "\n " + it.value }
                    .replace(regex3) { "\n    " + it.value }

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
                    Text(
                        text = main, fontSize = 14.sp, style = TayAppTheme.typo.typography.body2
                    )
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