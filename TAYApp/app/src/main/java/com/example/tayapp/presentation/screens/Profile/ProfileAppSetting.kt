package com.example.tayapp.presentation.screens.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tayapp.presentation.components.BadgePill
import com.example.tayapp.presentation.components.CardProfileListItem
import com.example.tayapp.presentation.components.CardProfileListItemWithNext
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun ProfileAppSetting(){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "앱 설정")
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithNext(
                icon = Icons.Outlined.Visibility,
                text = "보기"
            )
            CardProfileListItemWithNext(
                icon = Icons.Outlined.NotificationsNone,
                text = "알람"
            )
        }
    }
}

@Composable
@Preview
fun PreviewProfileSetting(){
    TayAppTheme {
        ProfileAppSetting()
    }
}