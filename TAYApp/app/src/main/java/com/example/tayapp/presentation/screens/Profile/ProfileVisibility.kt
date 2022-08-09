package com.example.tayapp.presentation.screens.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FormatSize
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tayapp.presentation.components.CardProfileListItemWithLink
import com.example.tayapp.presentation.components.CardProfileListItemWithNext
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun ProfileVisibility(
    upPress: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "앱 정보", upPress)
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithNext(
                icon = Icons.Outlined.LightMode,
                text = "모드",
                subtext = "시스템(라이트)"
            )
            CardProfileListItemWithNext(
                icon = Icons.Outlined.FormatSize,
                text = "글자 크기",
                subtext = "보통"
            )
        }
    }
}

