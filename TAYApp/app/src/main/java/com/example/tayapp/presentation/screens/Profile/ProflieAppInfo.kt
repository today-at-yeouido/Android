package com.example.tayapp.presentation.screens.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tayapp.presentation.components.CardProfileListItemWithLink
import com.example.tayapp.presentation.components.CardProfileListItemWithNext
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun ProfileAppInfo(){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "앱 정보")
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Description,
                text = "이용약관"
            )
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Description,
                text = "개인정보 정책"
            )
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Description,
                text = "오픈소스"
            )
        }
    }
}

@Composable
@Preview
fun PreviewProfileAppInfo(){
    TayAppTheme {
        ProfileAppInfo()
    }
}