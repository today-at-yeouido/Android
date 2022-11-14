package com.todayeouido.tayapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todayeouido.tayapp.R
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.utils.ExpandButton
import com.todayeouido.tayapp.presentation.utils.NotificationButton
import com.google.accompanist.flowlayout.FlowRow
import com.todayeouido.tayapp.presentation.ui.theme.dm_gray100
import com.todayeouido.tayapp.presentation.ui.theme.lm_gray100
import kotlinx.coroutines.CoroutineScope


/**
 * Tag data model 생성시 수정
 */
var datalist = listOf<List<String>>(
    listOf("전체", "전체"),
    listOf("행정안전위원회", "행정안전\uD83D\uDEA8"),
    listOf("보건복지위원회", "보건복지\uD83D\uDC89"),
    listOf("국토교통위원회", "국토교통\uD83D\uDEE3"),
    listOf("기획재정위원회", "기획재정\uD83E\uDE99"),
    listOf("환경노동위원회", "환경\uD83C\uDF33/노동\uD83D\uDC77\uD83C\uDFFB"),
    listOf("교육위원회", "교육\uD83D\uDCDA"),
    listOf("법제사법위원회", "사법⚖"),
    listOf("문화체육관광위원회", "문화체육관광\uD83C\uDF88"),
    listOf("정무위원회", "정무\uD83D\uDCBC"),
    listOf("농림축산식품해양수산위원회", "농림\uD83C\uDF3E/해양\uD83C\uDF0A"),
    listOf("과학기술정보방송통신위원회", "과학통신\uD83D\uDD2C"),
    listOf("산업통상자원중소벤처기업위원회", "산업기업\uD83C\uDFE2"),
    listOf("정보위원회", "정보\uD83D\uDCD1"),
    listOf("외교통일위원회", "외교통일\uD83C\uDF0F"),
    listOf("여성가족위원회", "여성가족\uD83E\uDEC2"),
    listOf("국방위원회", "국방\uD83E\uDE96"),
    listOf("국회운영위원회", "국회운영\uD83D\uDCE3"),
    listOf("기타", "기타➕")
)


@Composable
fun TayHomeTopAppBar(
    elevation: Dp = 0.dp,
    modifier: Modifier = Modifier,
    onTagClick: (Int) -> Unit,
    currentTag: Int,
//    listState: LazyListState,
//    scope: CoroutineScope,
    isExpanded: Boolean,
    onArrowClick: (Boolean) -> Unit
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()


    Column(
        modifier = modifier
            .statusBarsPadding()
    ) {
        TopAppBar(
            title = {
               Row(
                   horizontalArrangement = Arrangement.spacedBy(8.dp),
                   verticalAlignment = Alignment.CenterVertically
               ){
                   Image(
                       modifier = Modifier
                           .size(26.dp)
                           .clip(RoundedCornerShape(8.dp)),
                       painter = painterResource(id = R.drawable.ic_tay_logo_app),
                       contentDescription = "main_title_image"
                   )

                   Text(text = "오늘, 여의도", fontWeight = FontWeight.Bold, color = TayAppTheme.colors.bodyText, fontSize = 18.sp)
               }
            },
            elevation = elevation,
            backgroundColor = TayAppTheme.colors.background,
            actions = {
                NotificationButton()
            }
        )
        HomeTabBar(
            modifier = modifier,
            currentTag = currentTag,
            onTagClick = onTagClick,
            listState = listState,
            scope = scope,
            isExpanded = isExpanded,
            onArrowClick = onArrowClick
        )
        Divider(color = TayAppTheme.colors.layer2, thickness = 1.dp)
    }
}


/**
 * 상임위 태그가 있는 Top Bar
 */
@Composable
fun HomeTabBar(
    modifier: Modifier,
    currentTag: Int,
    onTagClick: (Int) -> Unit,
    listState: LazyListState,
    scope: CoroutineScope,
    isExpanded: Boolean,
    onArrowClick: (Boolean) -> Unit
){


    Row(){
        Box(
            modifier = Modifier
                .weight(7f)
        ){
            if(isExpanded)
                Row() {
                    Spacer(modifier = Modifier.width(16.dp))
                    ExpandedTagBar(
                        currentTag = currentTag,
                        onTagClick = onTagClick,
                        onArrowClick = onArrowClick
                    )
                }
            else
                NotExpandedTagBar(
                    currentTag = currentTag,
                    onTagClick = onTagClick,
                    listState = listState,
                    scope = scope,
                    isExpanded = isExpanded
                )
        }

        ExpandButton(
            isExpanded = isExpanded,
            onClick = {onArrowClick(!isExpanded)} )
    }


}




@Composable
private fun NotExpandedTagBar(
    modifier: Modifier = Modifier,
    currentTag: Int,
    onTagClick: (Int) -> Unit,
    listState: LazyListState,
    scope: CoroutineScope,
    isExpanded: Boolean
){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        state = listState,
        contentPadding = PaddingValues(start = 16.dp)
    ){
        itemsIndexed(datalist){ index , it->
            if(currentTag == index) TayTag(it[1], true, onClick = {onTagClick(index)})
            else TayTag(it[1],false,onClick = {onTagClick(index)})
        }
    }

    LaunchedEffect(isExpanded){
        listState.animateScrollToItem(index = currentTag)
    }
}

@Composable
private fun ExpandedTagBar(
    modifier: Modifier = Modifier,
    currentTag: Int,
    onTagClick: (Int) -> Unit,
    onArrowClick: (Boolean) -> Unit
){
    FlowRow(
        mainAxisSpacing = 7.dp,
        crossAxisSpacing = 7.dp,
        modifier = Modifier.padding(bottom = 10.dp)

    ){
        datalist.forEach{ it ->
            if(currentTag == datalist.indexOf(it)) TayTag(it[1], true,onClick = {onTagClick(datalist.indexOf(it));onArrowClick(false)})
            else TayTag(it[1],false,onClick = {onTagClick(datalist.indexOf(it)); onArrowClick(false)})
        }

    }
}





