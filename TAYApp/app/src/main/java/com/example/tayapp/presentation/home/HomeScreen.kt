package com.example.tayapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.components.TAYScaffold
import com.example.tayapp.presentation.ui.theme.TAYAppTheme

@Composable
fun HomeScreen() {
    Text(text = "HI", modifier = Modifier.fillMaxSize(), fontSize = 60.sp)

}

@Composable
private fun HomeTopAppBar(
    elevation: Dp = 0.dp
){
    TopAppBar(
        title = {
            Spacer(
                modifier = Modifier
                    .background(TAYAppTheme.colors.primary)
                    .width(100.dp)
                    .height(26.dp)
            )
        },
        elevation = elevation,
        backgroundColor = TAYAppTheme.colors.background,
        actions = {
            /**
             * Icon 파일 생성하면 onClick 함께 구현할 예정
             */
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = TAYAppTheme.colors.icon
                )
            }
        }
    )
}