package com.example.tayapp.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R
import com.example.tayapp.presentation.navigation.AppGraph
import com.example.tayapp.presentation.navigation.BottomBarDestination
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.utils.TayIcons

@Composable
fun TayBottomBar(
    tabs: Array<BottomBarTabs>,
    currentRoute: String? = null,
    navigateToRoute: (String) -> Unit = {},

    /** icon에 color 필요? */
    color: Color = TayAppTheme.colors.icon,
    contentColor: Color = TayAppTheme.colors.subduedIcon
) {
    val currentSection = tabs.first {
        it.route == currentRoute
    }

    BottomNavigation(
        backgroundColor = TayAppTheme.colors.background,
        modifier = Modifier
            .navigationBarsPadding()
            .height(56.dp)
    ) {
        tabs.forEach { section ->
            BottomNavigationItem(
                selected = section == currentSection,
                onClick = bottomNavigate(section, navigateToRoute),
                label = {
                    Text(
                        text = stringResource(id = section.title),
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                },
                icon = {
                    Icon(
                        imageVector = section.icon,
                        modifier = Modifier
                            .padding(3.dp)
                            .size(26.dp),
                        contentDescription = null,
                    )
                },
                selectedContentColor = TayAppTheme.colors.bodyText,
                unselectedContentColor = TayAppTheme.colors.subduedIcon
            )
        }
    }
}

@Composable
private fun bottomNavigate(
    section: BottomBarTabs,
    navigateToRoute: (String) -> Unit
) = if (section == BottomBarTabs.PROFILE) {
    { navigateToRoute(AppGraph.PROFILE_GRAPH) }
} else {
    { navigateToRoute(section.route) }
}


enum class BottomBarTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    Feed(R.string.bottom_home, TayIcons.bottombar_home, BottomBarDestination.FEED),
    SCRAP(R.string.bottom_scrap, TayIcons.bottombar_bookmark, BottomBarDestination.SCRAP),
    SEARCH(R.string.bottom_search, TayIcons.bottombar_search, BottomBarDestination.SEARCH),
    REPORT(R.string.bottom_report, TayIcons.bottombar_message, BottomBarDestination.REPORT),
    PROFILE(R.string.bottom_profile, TayIcons.bottombar_person, BottomBarDestination.PROFILE)
}
