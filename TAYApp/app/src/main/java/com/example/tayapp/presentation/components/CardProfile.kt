package com.example.tayapp.presentation.components

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.NavigateNextButton
import com.example.tayapp.presentation.utils.TayIcons

@Composable
fun CardUserProfile(){
    TayCard() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(Card_Inner_Padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                CardUserProfileText()
                TayButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(ButtonSmallWidth)
                        .height(ButtonSmallHeight),
                    backgroundColor = lm_gray000,
                    contentColor = lm_gray800,
                    border = BorderStroke(1.dp, lm_gray200)
                ) {
                    Text("프로필 수정", style = TayAppTheme.typo.typography.button)
                }
            }
            TayButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonMediumHeight),
                backgroundColor = lm_gray000,
                contentColor = lm_gray800,
                border = BorderStroke(1.dp, lm_gray200)

            ) {
                Text("뱃지 더보기", style = TayAppTheme.typo.typography.button)
                Icon(
                    imageVector = TayIcons.navigate_next,
                    contentDescription = null
                )

            }
        }
    }
}

@Composable
private fun CardUserProfileText(
    name: String = "사용자",
    email: String = "happy@gmail.com"
){
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text("${name} 님", style = TayAppTheme.typo.typography.h2)
        Text("$email",
            fontWeight = FontWeight.Light,
            color = lm_gray800,
            fontSize = 14.sp
        )
    }
}

/**
 * modifier로 background를 쓰면 크기조절을 못해서 Surface로 준다.
 */
@Composable
fun CardProfilSection(
    icon: ImageVector = TayIcons.setting_outlined,
    title: String = "설정",
    subTitle: String = "보기, 알람"
){
    TayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp)
        ){
            Box() {
                Surface(
                    color = lm_gray075,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.size(38.dp)
                ){}
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Text("$title",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Text("$subTitle",
                    fontWeight = FontWeight.Normal,
                    color = lm_gray600,
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = TayIcons.navigate_next,
                contentDescription = null,
                tint = lm_gray300
            )

        }
    }
}

@Composable
fun CardProfileListItem(
    icon: ImageVector = TayIcons.visibility_outlined,
    text: String = "입력",
    subtext: String = "",
    endComponent: @Composable RowScope. () -> Unit = {}
){
    Row(
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = ""
        )
        Text("$text",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Text("$subtext",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = lm_gray600
        )
        Row(content = endComponent)
    }
}

@Composable
fun CardProfileListItemWithLink(
    icon: ImageVector = TayIcons.visibility_outlined,
    text: String = "입력",
    subtext: String = "asdfjn"
){
    CardProfileListItem(
        icon = icon,
        text = text,
        subtext = subtext
    ){
        Icon(
            imageVector = TayIcons.north_east,
            contentDescription = null,
            tint = lm_gray300
        )
    }
}

@Composable
fun CardProfileListItemWithNext(
    icon: ImageVector = TayIcons.visibility_outlined,
    text: String = "입력",
    subtext: String = "asdfjn",
){
    CardProfileListItem(
        icon = icon,
        text = text,
        subtext = subtext
    ){
        Icon(
            imageVector = TayIcons.navigate_next,
            contentDescription = null,
            tint = lm_gray300
        )
    }
}

@Composable
fun CardProfileListItemWithOutIcon(
    text: String = "입력",
    subtext: String = "asdfjn",
    endComponent: @Composable RowScope. () -> Unit
) {
    CardProfileListItem(
        text = text,
        subtext = subtext,
        endComponent = endComponent
    )
}

@Composable
@Preview
private fun CardIserPreview(){
    TayAppTheme {
        CardUserProfile()
        //CardProfilSection()
//        CardProfileListItem(){
//            Icon(
//                imageVector = TayIcons.north_east,
//                contentDescription = null,
//                tint = lm_gray300
//            )
//        }
    }
}