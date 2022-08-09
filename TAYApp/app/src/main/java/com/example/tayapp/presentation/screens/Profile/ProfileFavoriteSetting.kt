package com.example.tayapp.presentation.screens.Profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.components.CardProfileListItemWithNext
import com.example.tayapp.presentation.components.TayCard
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.Emoij
import com.example.tayapp.presentation.utils.TayIcons

var favoritedatalist = listOf<String>(
    "행정안전",
    "보건복지",
    "국토교통",
    "기획재정",
    "환경노동",
    "교육",
    "사법",
    "문화체육관광",
    "정무",
    "농림식품해양",
    "과학기술통신",
    "산업기업",
    "정보",
    "외교통일",
    "여성가족",
    "국방",
    "국회운영",
    "기타"
)



@Composable
fun ProfileFavoriteSetting(
    upPress: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "관심분야 설정", upPress)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = KeyLine)
        ){
            item(span = { GridItemSpan(this.maxLineSpan) }){
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = KeyLine, vertical = 30.dp)
                ) {
                    Text("관심 있는 상임위원회를 설정해보세요!",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                    Text("선택한 상임위의 법안을 추천해드려요",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = lm_gray600
                    )
                }
            }
            items(favoritedatalist){ item ->
                ProfileFavoriteCard(title = item, isClicked = true)
            }
        }
    }
}

@Composable
fun ProfileFavoriteCard(
    title: String,
    subtitle: String = "국회운영 / 위원회 소관",
    isClicked: Boolean
){
    val checkedState = remember { mutableStateOf(true) }

    TayCard(
        modifier = Modifier.fillMaxWidth(),
        borderStroke = BorderStroke(1.dp, if(checkedState.value)lm_primary50 else TayAppTheme.colors.border)
    ) {
        Box(
            modifier = Modifier.padding(10.dp)
        ){
            IconToggleButton(
                modifier = Modifier
                    .padding(0.dp)
                    .size(Icon_Size),
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = !checkedState.value }
            ) {
                Icon(
                    imageVector = if (checkedState.value) TayIcons.check_circle else Icons.Outlined.Circle,
                    contentDescription = null,
                    tint = if (checkedState.value) lm_primary50 else lm_gray200,
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    "${Emoij[title]}",
                    fontSize = 36.sp
                )
                Text("$title",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 12.dp, bottom = 1.dp)
                )
                Text("$subtitle",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = lm_gray600
                )
            }
        }
    }
}



