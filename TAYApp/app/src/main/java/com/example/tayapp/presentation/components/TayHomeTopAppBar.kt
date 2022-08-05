package com.example.tayapp.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.utils.Emoij
import com.example.tayapp.presentation.utils.ExpandButton
import com.example.tayapp.presentation.utils.NotificationButton
import com.example.tayapp.presentation.utils.TayIcons
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Tag data model 생성시 수정
 */
var datalist = listOf<String>(
    "전체",
    "행정안전${Emoij["행정안전"]}",
    "보건복지${Emoij["보건복지"]}",
    "국토교통${Emoij["국토교통"]}",
    "기획재정${Emoij["기획재정"]}",
    "환경${Emoij["환경"]}/노동${Emoij["노동"]}",
    "교육${Emoij["교육"]}",
    "사법${Emoij["사법"]}",
    "문화체육관광${Emoij["문화체육관광"]}",
    "정무${Emoij["정무"]}",
    "농림${Emoij["농림"]}/해양${Emoij["해양"]}",
    "과학통신${Emoij["과학통신"]}",
    "산업기업${Emoij["산업기업"]}",
    "정보${Emoij["정보"]}",
    "외교통일${Emoij["외교통일"]}",
    "여성가족${Emoij["여성가족"]}",
    "국방${Emoij["국방"]}",
    "국회운영${Emoij["국회운영"]}",
    "기타"
)


@Composable
fun TayHomeTopAppBar(
    elevation: Dp = 0.dp,
    modifier: Modifier = Modifier,
//    onTagClick: (String) -> Unit = {},
//    currentTag: String,
//    listState: LazyListState,
//    scope: CoroutineScope,
//    isExpanded: Boolean
) {
    var currentTag by remember{ mutableStateOf("전체") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isExpanded by remember{ mutableStateOf(false)}


    Column(
        modifier = modifier
            .statusBarsPadding()
    ) {
        TopAppBar(
            title = {
                Spacer(
                    modifier = Modifier
                        .background(TayAppTheme.colors.primary)
                        .width(100.dp)
                        .height(26.dp)
                )
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
            onTagClick = { it ->
                currentTag = it
                isExpanded = false
                         },
            listState = listState,
            scope = scope,
            isExpanded = isExpanded,
            onArrowClick = {
                isExpanded = !isExpanded;
            }
        )
    }
}




@Composable
fun HomeTabBar(
    modifier: Modifier,
    currentTag: String,
    onTagClick: (String) -> Unit,
    listState: LazyListState,
    scope: CoroutineScope,
    isExpanded: Boolean,
    onArrowClick: () -> Unit
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
                        onTagClick = onTagClick
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
            onClick = onArrowClick )
    }


}




@Composable
private fun NotExpandedTagBar(
    modifier: Modifier = Modifier,
    currentTag: String,
    onTagClick: (String) -> Unit,
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
        items(datalist){ it ->
            if(currentTag == it) TayTag(it, true, onTagClick)
            else TayTag(it,false,onClick = onTagClick)
        }
    }

    LaunchedEffect(isExpanded){
        listState.animateScrollToItem(index = datalist.indexOf(currentTag))
    }
}

@Composable
private fun ExpandedTagBar(
    modifier: Modifier = Modifier,
    currentTag: String,
    onTagClick: (String) -> Unit
){
    FlowRow(
        mainAxisSpacing = 7.dp,
        crossAxisSpacing = 7.dp,
        modifier = Modifier.padding(bottom = 10.dp)

    ){
        datalist.forEach{ it ->
            if(currentTag == it) TayTag(it, true,onTagClick)
            else TayTag(it,false,onClick = onTagClick)
        }

    }
}





