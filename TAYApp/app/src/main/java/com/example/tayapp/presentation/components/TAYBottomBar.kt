package com.example.tayapp.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R
import com.example.tayapp.presentation.ui.theme.TAYAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray000
import com.example.tayapp.presentation.ui.theme.lm_gray400
import com.example.tayapp.presentation.ui.theme.lm_gray700

@Composable
fun TAYBottomBar(
    tabs: Array<BottomBarTabs>,
    currentRoute: String? = null,
    navigateToRoute: (String) -> Unit = {},

    /** icon에 color 필요? */
    color: Color = TAYAppTheme.colors.icon,
    contentColor: Color = TAYAppTheme.colors.subduedIcon
) {
    BottomNavigation(
        backgroundColor = lm_gray000,
        modifier = Modifier.height(56.dp)
    ) {
        tabs.forEach { item ->
            BottomNavigationItem(
                selected = stringResource(id = item.title) == "홈",
                onClick = { },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        style = TAYAppTheme.typo.typography.body1,
                        fontSize = 15.sp
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        modifier = Modifier.size(26.dp),
                        contentDescription = null,
                    )
                },
                selectedContentColor = lm_gray700,
                unselectedContentColor = lm_gray400
            )
        }
    }
}


enum class BottomBarTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.bottom_home, Icons.Outlined.Home, "home/home"),
    SCRAP(R.string.bottom_scrap, Icons.Outlined.Bookmark, "home/scrap"),
    SEARCH(R.string.bottom_search, Icons.Outlined.Search, "home/search"),
    REPORT(R.string.bottom_report, Icons.Outlined.Message, "home/report"),
    PROFILE(R.string.bottom_profile, Icons.Outlined.Person, "home/profile")
}
