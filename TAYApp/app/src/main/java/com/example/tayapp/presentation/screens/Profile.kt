package com.example.tayapp.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons

@Composable
fun Profile(modifier: Modifier) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    lm_primary50,
                    RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                ),

        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = KeyLine),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(50.dp)
            )
            CardUserProfile()
            ProfileSettings()
            TayDivider()
            ProfileLineItems()
            ProfileBottomButtons()
        }
    }
}

@Composable
private fun ProfileSettings(){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CardProfilSection(
            icon = Icons.Filled.AccountCircle,
            title = "계정",
            subTitle = "관심분야 설정, 구독서비스"
        )
        CardProfilSection(
            icon = Icons.Outlined.Settings,
            title = "앱 설정",
            subTitle = "보기, 알람"
        )
        CardProfilSection(
            icon = Icons.Outlined.HelpOutline,
            title = "문의하기",
            subTitle = "FAQ, 이메일 문의"
        )
        CardProfilSection(
            icon = Icons.Outlined.Info,
            title = "앱 정보",
            subTitle = "이용약관, 개인정보 정책, 오픈소스"
        )
    }
}

@Composable
private fun ProfileLineItems(){
    Column() {
        CardProfileListItemWithOutIcon(
            text = "공지사항"
        ){
            Icon(
                imageVector = TayIcons.navigate_next,
                contentDescription = null,
                tint = lm_gray300
            )
        }
        CardProfileListItemWithOutIcon(
            text = "버전",
            subtext = "v.1.0.0"
        ){
            TayButton(
                onClick = { /*TODO*/ },
                contentColor = lm_gray800
            ) {
                Text(
                    text = "업데이트",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun ProfileBottomButtons(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TayButton(
            onClick = { /*TODO*/ },
            contentColor = lm_gray800,
            backgroundColor = lm_gray000,
            border = BorderStroke(1.dp, lm_gray200)
        ) {
            Text(
                text = "로그아웃",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
        TayButton(
            onClick = { /*TODO*/ },
            contentColor = lm_gray800,
            backgroundColor = lm_gray000,
            border = BorderStroke(1.dp, lm_gray200)
        ) {
            Text(
                text = "회원 탈퇴",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }

}
