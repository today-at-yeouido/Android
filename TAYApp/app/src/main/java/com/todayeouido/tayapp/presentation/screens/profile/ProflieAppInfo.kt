package com.todayeouido.tayapp.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.todayeouido.tayapp.presentation.components.CardProfileListItemWithLink
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine

//이용약관
const val TOS = "https://grandiose-nylon-a50.notion.site/e2dca65874374744aa702d3030e88f8c"
const val PRIVACY_POLICY = "https://grandiose-nylon-a50.notion.site/6408e668538946c0b604f18f8223802c"
const val APPINFO = "https://grandiose-nylon-a50.notion.site/33c917da3cc846e58227e78850c684f4"
const val OPEN_SOURCE = "https://grandiose-nylon-a50.notion.site/f6e7b2d8533b4da49a0692b04484ca77"
const val NOTICE = "https://grandiose-nylon-a50.notion.site/0204fd1804fb4c249b439f965d99cb44"

@Composable
fun ProfileAppInfo(
    upPress: () -> Unit,
){
    val mUriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "앱 정보", upPress)
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Description,
                text = "이용약관",
                onClick = { mUriHandler.openUri(TOS) }
            )
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Description,
                text = "개인정보 정책",
                onClick = { mUriHandler.openUri(PRIVACY_POLICY) }
            )
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Description,
                text = "오픈소스",
                onClick = { mUriHandler.openUri(OPEN_SOURCE) }
            )
            CardProfileListItemWithLink(
                icon = Icons.Outlined.Description,
                text = "앱 정보",
                onClick = { mUriHandler.openUri(APPINFO) }
            )
        }
    }
}

