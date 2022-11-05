package com.todayeouido.tayapp.presentation.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todayeouido.tayapp.presentation.components.TayCard
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.ui.theme.*
import com.todayeouido.tayapp.presentation.utils.Emoji
import com.todayeouido.tayapp.presentation.utils.TayIcons
import com.todayeouido.tayapp.presentation.viewmodels.FavoritCategoryViewModel

var favoritedatalist = listOf<List<String>>(
    listOf("행정안전위원회", "행정안전"),
    listOf("보건복지위원회", "보건복지"),
    listOf("국토교통위원회", "국토교통"),
    listOf("기획재정위원회", "기획재정"),
    listOf("환경노동위원회", "환경노동"),
    listOf("교육위원회", "교육"),
    listOf("법제사법위원회", "사법"),
    listOf("문화체육관광위원회", "문화체육관광"),
    listOf("정무위원회", "정무"),
    listOf("농림축산식품해양수산위원회", "농림식품해양"),
    listOf("과학기술정보방송통신위원회", "과학기술통신"),
    listOf("산업통상자원중소벤처기업위원회", "산업기업"),
    listOf("정보위원회", "정보"),
    listOf("외교통일위원회", "외교통일"),
    listOf("여성가족위원회", "여성가족"),
    listOf("국방위원회", "국방"),
    listOf("국회운영위원회", "국회운영"),
    listOf("기타", "기타")
)



@Composable
fun ProfileFavoriteSetting(
    upPress: () -> Unit
){
    val viewModel = hiltViewModel<FavoritCategoryViewModel>()
    val favoritCategory by viewModel.favoritCategoryState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "관심분야 설정", {upPress(); viewModel.saveCategory()})
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
                        color = TayAppTheme.colors.subduedText
                    )
                }
            }
            items(favoritedatalist){ item ->
                ProfileFavoriteCard(title = item[1], isClicked = favoritCategory.favoritCategory.contains(item[0]), onCardClick = {viewModel.clickCategory(item[0])})
            }
        }
    }
}

@Composable
fun ProfileFavoriteCard(
    title: String,
    subtitle: String = "국회운영 / 위원회 소관",
    isClicked: Boolean,
    onCardClick: () -> Unit
){

    TayCard(
        modifier = Modifier.fillMaxWidth(),
        enable = true,
        borderStroke = BorderStroke(1.dp, if(isClicked)TayAppTheme.colors.primary else TayAppTheme.colors.border),
        onClick = onCardClick
    ) {
        Box(
            modifier = Modifier.padding(10.dp)
        ){

            Icon(
                imageVector = if (isClicked) TayIcons.check_circle else Icons.Outlined.Circle,
                contentDescription = null,
                tint = if (isClicked) TayAppTheme.colors.primary else TayAppTheme.colors.border,
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    "${Emoji[title]}",
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
                    color = TayAppTheme.colors.subduedText
                )
            }
        }
    }
}



