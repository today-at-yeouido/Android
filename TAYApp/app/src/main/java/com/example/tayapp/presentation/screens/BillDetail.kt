package com.example.tayapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.data.remote.dto.bill.NewsDto
import com.example.tayapp.domain.model.DetailBill
import com.example.tayapp.domain.use_case.Article
import com.example.tayapp.domain.use_case.Condolences
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray600
import com.example.tayapp.presentation.utils.StateColor
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.presentation.viewmodels.DetailViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BillDetail(
    viewModel: DetailViewModel = hiltViewModel(),
    upPress: () -> Unit,
    toTable: () -> Unit,
    onGroupBillSelected: (Int) -> Unit = {}
) {
    val billId = viewModel.billId
    val detailState = viewModel.detailState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val mUriHandler = LocalUriHandler.current

    val table by viewModel.table.collectAsState()

    BottomSheetScaffold(
        modifier = Modifier.navigationBarsPadding(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { BillProgressDetail() },
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetPeekHeight = 10.dp,
        backgroundColor = TayAppTheme.colors.background,
        sheetBackgroundColor = TayAppTheme.colors.layer1,
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                snackbar = { data ->
                    TaySnackbar(
                        snackbarData = data,
                        imageVector = if (UserState.isLogin()) Icons.Filled.CheckCircle else Icons.Filled.Dangerous,
                        imageColor = if (UserState.isLogin()) TayAppTheme.colors.success2 else TayAppTheme.colors.danger2
                    )
                }
            )
        }
    ) {
        Column {
            TayTopAppBarWithScrap(
                "의안상세",
                upPress,
                isBookMarked = detailState.value.billDetail.isScrapped,
                onClickScrap = {
                    coroutineScope.launch {
                        if (detailState.value.billDetail.isScrapped) viewModel.deleteScrap(billId) else viewModel.addScrap(
                            billId
                        )
                        bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                            message = "",
                            actionLabel =
                            if (UserState.isLogin()) {
                                if (!detailState.value.billDetail.isScrapped) "스크랩 되었습니다." else "스크랩 취소 되었습니다."
                            } else {
                                "로그인이 필요합니다!"
                            }
                        )
                    }
                }
            )

            if (UserState.network) {
                if (detailState.value.isLoading) {
                    LoadingView(modifier = Modifier.fillMaxSize())
                } else {
                    LazyColumn() {
                        item {
                            DetailHeader(onProgressClick = {
                                coroutineScope.launch {
                                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    } else {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                    }
                                }
                            }, bill = detailState.value.billDetail)

                            Column(
                                modifier = Modifier
                                    .padding(
                                        horizontal = KeyLine,
                                        vertical = 24.dp
                                    ),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                if (detailState.value.billDetail.isPlenary && detailState.value.billDetail.plenaryInfo.total!=0) {
                                    CardPieGraph(detailState.value.billDetail)
                                }

                                BillPointText(detailState.value.billDetail.summary)
                                if (table.billTable != null) BillRevisionText(
                                    table.billTable!!.condolences,
                                    toTable
                                )
                                if (detailState.value.billDetail.news.isNotEmpty()) NewsHeader()
                            }
                        }

                        items(detailState.value.billDetail.news) { news ->
                            CardNews(
                                imageURL = news.imgUrl,
                                title = news.newsName,
                                date = news.pubDate,
                                press = news.newsFrom,
                                newsLink = news.newsLink,
                                mUriHandler = mUriHandler
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }
            } else {
                NetworkErrorScreen {
                    UserState.network = true
                    viewModel.tryGetBillDetail()
                }
            }
        }
    }
}


@Composable
fun DetailHeader(bill: DetailBill, onProgressClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                StateColor(status = bill.status),
                RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
            )
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        DetailTitle(bill)
        CardCommittee(bill.committeeInfo.committee)
        CardBillProgress(
            onProgressClick,
            bill.progressItems,
            bill.proposeDt
        )

    }
}

@Composable
fun DetailTitle(bill: DetailBill) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = KeyLine),

        ) {
        Spacer(modifier = Modifier.height(20.dp))
        PillList(bill.billType, bill.status)

        Text(
            fontSize = 24.sp,
            color = TayAppTheme.colors.headText,
            text = bill.billName,
            style = TayAppTheme.typo.typography.h1
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = bill.proposer,
                fontSize = 12.sp,
                color = TayAppTheme.colors.subduedText,
                fontWeight = FontWeight.Normal
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = TayIcons.visibility_outlined,
                    contentDescription = "null",
                    tint = lm_gray600,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${bill.views}",
                    fontSize = 12.sp,
                    color = lm_gray600,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun CardCommittee(
    committee: String? = null
) {
    TayCard(
        modifier = Modifier.padding(horizontal = KeyLine)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Card_Inner_Padding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "상임위원회",
                    fontSize = 16.sp,
                    color = TayAppTheme.colors.bodyText,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = TayIcons.help,
                    contentDescription = "null",
                    tint = TayAppTheme.colors.information2,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = if (committee.isNullOrBlank()) "미정" else committee,
                fontSize = 16.sp,
                color = TayAppTheme.colors.subduedText,
                fontWeight = FontWeight.Normal
            )

        }

    }
}


@Composable
private fun CardPieGraph(
    bill: DetailBill
) {
    TayCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 11.dp)
        ) {
            Text(
                text = "본 회의 투표 결과",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = TayAppTheme.colors.bodyText,
                modifier = Modifier.padding(horizontal = 3.dp),
                maxLines = 1
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PieGraph(
                    listOf(
                        bill.plenaryInfo.approval,
                        bill.plenaryInfo.opposition,
                        bill.plenaryInfo.abstention,
                        bill.plenaryInfo.total
                    )
                )
            }
        }
    }
}

/**
 * 법안 상세 내용
 * 1. '이에,' '  이에' 가 포함되어있으면 하이라이팅
 * 2. 제안이유, 주요내용이 있으면 글자 스타일을 부제목 으로
 * 3. 그 외에는 기본 스타일
 */
@Composable
private fun BillPointText(
    summary: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title(string = "법안 핵심 내용")

        summary.forEach {

            if(it.contains("이에,") || it.startsWith("  이에")) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(background = TayAppTheme.colors.caution1)) {
                            append(it)
                        }
                    },
                    color = TayAppTheme.colors.headText,
                    style = TayAppTheme.typo.typography.body2
                )
            } else if(it.contains("제안이유") || it.contains("주요내용")) {
                Text(
                    text = it,
                    color = TayAppTheme.colors.bodyText,
                    style = TayAppTheme.typo.typography.body1
                )
            } else if(!it.isNullOrBlank()) {
                Text(
                    text = it,
                    color = TayAppTheme.colors.headText,
                    style = TayAppTheme.typo.typography.body2
                )
            }
        }

    }
}

@Composable
private fun BillDetailNews(
    news: List<NewsDto>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {

    }
}

@Composable
private fun NewsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(top = 27.dp)
    ) {
        Title(string = "관련 뉴스")
    }
}

/**
 * pill수정 필요
 */
@Composable
private fun BillRevisionText(
    billTable: List<Condolences>,
    toTable: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Title(string = "개정 내용 확인하기")

        RevisionSection(billTable)

        TayButton(
            onClick = toTable,
            modifier = Modifier
                .fillMaxWidth(),
            contentColor = TayAppTheme.colors.background,
            backgroundColor = TayAppTheme.colors.bodyText
        ) {
            Text("개정 내용 확인하기", style = TayAppTheme.typo.typography.button)
            Icon(
                imageVector = TayIcons.navigate_next,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun RevisionSection(billTable: List<Condolences>) {
    TayCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Card_Inner_Padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "개정된 조항",
                color = TayAppTheme.colors.bodyText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                billTable.forEach { article ->
                    if (article is Article) {
                        val title = getRevisionTitle(article)
                        BillRevisionItem(title, article.type)
                    }
                }
            }
        }
    }
}


@Composable
fun getRevisionTitle(
    article: Condolences,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = TayAppTheme.colors.subduedText,
    inlineTextContent: Set<String> = emptySet()
) =
    buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontWeight = fontWeight,
                fontSize = fontSize,
                color = color
            )
        ) {
            /** 타이틀 */
            article.title.let {
                val text = article.title.text
                var temp = it.text
                if (it.row.isNotEmpty() && !(article.type.contains("신설") || article.type.contains("삭제"))) {
                    it.row.forEach { row ->
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
                } else append(text)
            }


            if (inlineTextContent.isNotEmpty()) {
                inlineTextContent.forEach {
                    append(" ")
                    appendInlineContent(it)
                }
            }
        }
    }

@Composable
private fun BillRevisionItem(title: AnnotatedString, type: Set<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row {
            for (i in type)
                ClausePill(clause = i)
        }
    }
    Text(
        text = title,
        color = TayAppTheme.colors.subduedText,
        style = TayAppTheme.typo.typography.body2
    )
}
