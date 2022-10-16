package com.todayeouido.tayapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun StateColor(status: String): Color {
    when(status){
        "소관위심사보고", "소관위심사", "본회의부의안건", "정부이송" -> return TayAppTheme.colors.caution2
        "공포", "본회의의결" -> return TayAppTheme.colors.success2
        "소관위접수", "접수" -> return TayAppTheme.colors.disableIcon
        "철회", "대안반영폐기", "본회의불부의", "회송" -> return TayAppTheme.colors.danger2
        else -> return TayAppTheme.colors.disableIcon
    }
}