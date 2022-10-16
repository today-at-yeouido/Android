package com.todayeouido.tayapp.presentation.screens.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Announcement
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import com.todayeouido.tayapp.presentation.components.CardProfileListItemWithLink
import com.todayeouido.tayapp.presentation.components.CardProfileListItemWithNext
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme

const val FAQ = "https://grandiose-nylon-a50.notion.site/FAQ-da4051eb51984ef0b1f04f30b09f8ac9"
const val EMAIL_REQUEST = "https://grandiose-nylon-a50.notion.site/FAQ-da4051eb51984ef0b1f04f30b09f8ac9"

@Composable
fun ProfileInquire(
    upPress: () -> Unit
){

    val mUriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "문의하기", upPress)
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Announcement,
                text = "FAQ",
                onClick = { mUriHandler.openUri(FAQ) }
            )
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Mail,
                text = "이메일 문의",
                onClick = { mUriHandler.openUri(EMAIL_REQUEST) }
            )
        }
    }
}

