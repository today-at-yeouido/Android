package com.todayeouido.tayapp.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.ui.theme.*

@Composable
fun ProfileAlarm(
    upPress: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "알람", upPress)
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileToggle(
                title = "스크랩한 법안",
                subTitle = "업데이트되면 알려드려요"
            )
            CardProfileToggle(
                title = "마케팅 알림",
                subTitle = "다양한 이벤트와 소식을 알려드려요"
            )
        }
    }
}

@Composable
fun CardProfileToggle(
    title: String = "설정",
    subTitle: String = "보기, 알람"
) {
    val checkedState = remember { mutableStateOf(true) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "$title",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Text(
                "$subTitle",
                fontWeight = FontWeight.Normal,
                color = TayAppTheme.colors.subduedText,
                fontSize = 12.sp
            )
        }
        Switch(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            modifier = Modifier.padding(0.dp),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = TayAppTheme.colors.background,
                uncheckedTrackColor = TayAppTheme.colors.layer2,
                checkedThumbColor = TayAppTheme.colors.background,
                checkedTrackColor = TayAppTheme.colors.primary
            )
        )

    }
}