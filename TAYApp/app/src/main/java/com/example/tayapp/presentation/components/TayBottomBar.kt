package com.example.tayapp.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tayapp.R
import com.example.tayapp.presentation.home.*
import com.example.tayapp.presentation.navigation.BottomBarDestination
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray000
import com.example.tayapp.presentation.ui.theme.lm_gray400
import com.example.tayapp.presentation.ui.theme.lm_gray700

fun NavGraphBuilder.addHomeGraph() {
    composable(route = BottomBarTabs.Feed.route) { from ->
        Feed(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.SCRAP.route) { from ->
        Search(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.SEARCH.route) { from ->
        Cart(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.REPORT.route) {
        Report(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.PROFILE.route) {
        Profile(Modifier.fillMaxSize())
    }
}

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
        backgroundColor = lm_gray000,
        modifier = Modifier.height(56.dp)
    ) {
        tabs.forEach { section ->
            BottomNavigationItem(
                selected = section == currentSection,
                onClick = { navigateToRoute(section.route) },
                label = {
                    Text(
                        text = stringResource(id = section.title),
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                },
                icon = {
                    Icon(
                        imageVector = section.icon,
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
    Feed(R.string.bottom_home, Icons.Outlined.Home, BottomBarDestination.FEED),
    SCRAP(R.string.bottom_scrap, Icons.Outlined.Bookmark, BottomBarDestination.SCRAP),
    SEARCH(R.string.bottom_search, Icons.Outlined.Search, BottomBarDestination.SEARCH),
    REPORT(R.string.bottom_report, Icons.Outlined.Message, BottomBarDestination.REPORT),
    PROFILE(R.string.bottom_profile, Icons.Outlined.Person, BottomBarDestination.PROFILE)
}
