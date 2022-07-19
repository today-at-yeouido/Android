package com.example.tayapp.presentation.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.BottomBarTabs
import com.example.tayapp.presentation.ui.theme.TayAppTheme


@Composable
private fun HomeTopAppBar(
    elevation: Dp = 0.dp
) {
    TopAppBar(
        title = {
            Spacer(
                modifier = Modifier
                    .background(TayAppTheme.colors.primary)
                    .width(100.dp)
                    .height(26.dp)
            )
        },
        elevation = elevation,
        backgroundColor = TayAppTheme.colors.background,
        actions = {
            /**
             * Icon 파일 생성하면 onClick 함께 구현할 예정
             */
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = TayAppTheme.colors.icon
                )
            }
        }
    )
}
