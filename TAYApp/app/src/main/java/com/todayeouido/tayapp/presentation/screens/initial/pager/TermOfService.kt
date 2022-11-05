package com.todayeouido.tayapp.presentation.screens.initial.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.todayeouido.tayapp.presentation.components.ButtonLargeHeight
import com.todayeouido.tayapp.presentation.components.TayButton
import com.todayeouido.tayapp.presentation.components.TayCheckbox
import com.todayeouido.tayapp.presentation.screens.profile.PRIVACY_POLICY
import com.todayeouido.tayapp.presentation.screens.profile.TOS
import com.todayeouido.tayapp.presentation.ui.theme.*
import com.todayeouido.tayapp.presentation.utils.TayIcons
import com.todayeouido.tayapp.utils.matchWidth
import com.todayeouido.tayapp.utils.textDp


@Composable
fun BoxScope.TermsOfService(
    onClick: () -> Unit
) {

    val mUriHandler = LocalUriHandler.current
    var bool1 by remember { mutableStateOf(false) }
    var bool2 by remember { mutableStateOf(false) }
    var bool3 by remember { mutableStateOf(false) }
    var buttonEnable = bool2 && bool3

    Column {

        Spacer(Modifier.height(10.dp))
        Text(
            "오늘, 여의도 서비스 이용약관에 \n동의하여 주세요",
            color = TayAppTheme.colors.headText,
            style = TayAppTheme.typo.typography.h3
        )
        Spacer(modifier = Modifier.height(26.dp))
        Row(
            modifier = Modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool1,
                onCheckedChange = {
                    if (!bool1) {
                        bool1 = true
                        bool2 = true
                        bool3 = true
                        buttonEnable = true
                    } else bool1 = false
                }
            )
            Text(
                "아래 약관에 모두 동의합니다",
                fontWeight = FontWeight.Medium,
                color = TayAppTheme.colors.bodyText,
                fontSize = 16.textDp,
                modifier = Modifier.width(260.matchWidth)
            )
        }
        Divider(color = TayAppTheme.colors.layer3)
        Row(
            modifier = Modifier
                .height(44.dp)
                .clickable { mUriHandler.openUri(TOS) },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool2,
                onCheckedChange = { bool2 = !bool2 },
            )
            Text(
                "[필수] 서비스 이용약관", fontWeight = FontWeight.Normal, color = TayAppTheme.colors.subduedText,
                modifier = Modifier.width(260.matchWidth)
            )
            Icon(imageVector = TayIcons.navigate_next, contentDescription = "")
        }
        Row(
            modifier = Modifier
                .height(44.dp)
                .clickable { mUriHandler.openUri(PRIVACY_POLICY) },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool3,
                onCheckedChange = { bool3 = !bool3 },
            )
            Text(
                "[필수] 개인정보 처리방침",
                fontWeight = FontWeight.Normal,
                color = TayAppTheme.colors.subduedText,
                modifier = Modifier.width(260.matchWidth)
            )
            Icon(imageVector = TayIcons.navigate_next, contentDescription = "")
        }

    }



    TayButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonLargeHeight)
            .align(Alignment.BottomCenter),
        contentColor = if (buttonEnable) TayAppTheme.colors.background else TayAppTheme.colors.disableText,
        backgroundColor = if (buttonEnable) TayAppTheme.colors.headText else TayAppTheme.colors.layer3,
        enabled = buttonEnable
    ) {
        Text("다음", style = TayAppTheme.typo.typography.button)
    }
}

