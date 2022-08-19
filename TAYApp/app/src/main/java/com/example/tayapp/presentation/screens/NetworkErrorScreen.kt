package com.example.tayapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun NetworkErrorScreen(tryGetData: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(TayAppTheme.colors.background)
    ) {
        Text(
            "네트워크 에러 !!",
            fontSize = 30.sp,
            color = TayAppTheme.colors.bodyText,
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    UserState.network = true
                    tryGetData()
                }
        )
    }

}