package com.todayeouido.tayapp.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.todayeouido.tayapp.presentation.components.CardProfileListItemWithNext
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.navigation.ProfileDestination
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine

@Composable
fun ProfileAppSetting(
    navController: NavController,
    upPress: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "앱 설정", upPress)
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithNext(
                icon = Icons.Outlined.Visibility,
                text = "보기",
                onClick = { navController.navigate(ProfileDestination.VISIBILITY) }
            )
            CardProfileListItemWithNext(
                icon = Icons.Outlined.NotificationsNone,
                text = "알람",
                onClick = { navController.navigate(ProfileDestination.ALARM) }
            )
        }
    }
}

