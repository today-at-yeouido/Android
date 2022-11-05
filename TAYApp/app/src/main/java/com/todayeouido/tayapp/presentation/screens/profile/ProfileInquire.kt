package com.todayeouido.tayapp.presentation.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Announcement
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.content.ContextCompat.startActivity
import com.todayeouido.tayapp.presentation.components.CardProfileListItemWithLink
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine

const val FAQ = "https://grandiose-nylon-a50.notion.site/FAQ-da4051eb51984ef0b1f04f30b09f8ac9"
const val EMAIL_REQUEST = "https://grandiose-nylon-a50.notion.site/FAQ-da4051eb51984ef0b1f04f30b09f8ac9"

@Composable
fun ProfileInquire(
    upPress: () -> Unit
){
    val emailAddress = "todayatyeouido@gmail.com"

    val mailIntent = Intent(Intent.ACTION_SENDTO) // 메일 전송 설정
        .apply {
            type = "text/plain" // 데이터 타입 설정
            data = Uri.parse("mailto:") // 이메일 앱에서만 인텐트 처리되도록 설정

            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress)) // 메일 수신 주소 목록
        }
    val context = LocalContext.current




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
                onClick = { startActivity(context, mailIntent, null) }
            )
        }
    }
}

