package com.example.tayapp.presentation.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.Icon_Padding
import com.example.tayapp.presentation.ui.theme.Icon_Size
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray200


object TayIcons {

    val bottombar_home = Icons.Outlined.Home
    val bottombar_bookmark = Icons.Outlined.BookmarkBorder
    val bottombar_search = Icons.Outlined.Search
    val bottombar_message = Icons.Outlined.Message
    val bottombar_person = Icons.Outlined.Person

    val bottombar_home_click = Icons.Filled.Home
    val bottombar_bookmark_click = Icons.Filled.Bookmark
    val bottombar_search_click = Icons.Filled.Search
    val bottombar_message_click = Icons.Filled.Message
    val bottombar_person_click = Icons.Filled.Person

    val topbar_notification = Icons.Outlined.Notifications
    val topbar_up = Icons.Default.KeyboardArrowUp
    val topbar_down = Icons.Default.KeyboardArrowDown

    val setting_outlined = Icons.Outlined.Settings
    val setting_filled = Icons.Filled.Settings

    val notification_outlined = Icons.Outlined.Notifications
    val notification_filled = Icons.Filled.Notifications
    val notification_active_filled = Icons.Filled.NotificationsActive
    val notification_off_filled = Icons.Filled.NotificationsOff

    val visibility_outlined = Icons.Outlined.Visibility
    val visibility_filled = Icons.Filled.Visibility
    val visibility_off = Icons.Filled.VisibilityOff

    val dark_mode_outlined = Icons.Outlined.DarkMode
    val dark_mode_filled = Icons.Filled.DarkMode

    val light_mode_outlined = Icons.Outlined.LightMode
    val light_mode_filled = Icons.Filled.LightMode

    val format_size_filled = Icons.Filled.FormatSize

    val info_outlined = Icons.Outlined.Info
    val info_filled = Icons.Filled.Info

    val private_tip = Icons.Outlined.PrivacyTip

    val sms_outlined = Icons.Outlined.Sms
    val sms_filled = Icons.Filled.Sms

    val campaign_outlined = Icons.Outlined.Campaign
    val campaign_filled = Icons.Filled.Campaign

    val arrowback = Icons.Filled.ArrowBack
    val arrowback_ios = Icons.Filled.ArrowBackIos

    val navigate_next = Icons.Filled.NavigateNext
    val navigate_before = Icons.Filled.NavigateBefore
    val expand_more = Icons.Outlined.ExpandMore
    val expand_less = Icons.Outlined.ExpandLess
    val north_east = Icons.Outlined.NorthEast

    val close = Icons.Outlined.Close
    val more_horiz = Icons.Filled.MoreHoriz
    val sync_alt = Icons.Filled.SyncAlt
    val check = Icons.Outlined.Check
    val refresh = Icons.Outlined.Refresh
    val history = Icons.Outlined.History
    val check_box_outline_blank = Icons.Outlined.CheckBoxOutlineBlank
    val check_box = Icons.Filled.CheckBox

    val help = Icons.Filled.Help
    val warning = Icons.Filled.Warning
    val cancle = Icons.Filled.Cancel
    val error = Icons.Filled.Error
    val check_circle = Icons.Filled.CheckCircle
    val danger = Icons.Filled.Dangerous

    val poll = Icons.Outlined.Poll
    val stacked_bar_chart = Icons.Filled.StackedBarChart
    val timeline = Icons.Outlined.Timeline
    val sort = Icons.Outlined.Sort

    val card_article = Icons.Outlined.Article
    val event = Icons.Filled.Event
    val expand = Icons.Filled.ExpandMore
}

object TayEmoji {
    val card_emoji: String = "🏗"
}

@Composable
fun NotificationButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    tint: Color = TayAppTheme.colors.icon
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(Icon_Padding)
            .size(Icon_Size)
    ) {
        Icon(
            imageVector = TayIcons.notification_outlined,
            contentDescription = null,
            tint = tint
        )
    }
}
/**
 * bottom padding 넣은거 수정해야함
 */
@Composable
fun ExpandButton(
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = isExpanded,
        onCheckedChange = { onClick() }
    ) {
        Icon(
            modifier = Modifier.padding(bottom = 15.dp),
            imageVector = if (isExpanded) TayIcons.expand_less else TayIcons.expand_more,
            contentDescription = null,
            tint = TayAppTheme.colors.icon
        )
    }
}


@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        modifier = modifier
            .padding(Icon_Padding)
            .size(Icon_Size),
        checked = isBookmarked,
        onCheckedChange = { onClick() }
    ) {
        Icon(
            imageVector = if (isBookmarked) TayIcons.bottombar_bookmark_click else TayIcons.bottombar_bookmark,
            contentDescription = null,
            tint = TayAppTheme.colors.icon
        )
    }
}

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    tint: Color = TayAppTheme.colors.icon,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(Icon_Padding)
            .size(Icon_Size)
    ) {
        Icon(
            imageVector = TayIcons.bottombar_search,
            contentDescription = null,
            tint = tint
        )
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    tint: Color = TayAppTheme.colors.icon
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(Icon_Size)
    ) {
        Icon(
            imageVector = TayIcons.arrowback,
            contentDescription = null,
            tint = tint
        )
    }
}

@Composable
fun NavigateNextButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    tint: Color = TayAppTheme.colors.icon
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(Icon_Size)
    ) {
        Icon(
            imageVector = TayIcons.navigate_next,
            contentDescription = null,
            tint = tint
        )
    }
}

@Composable
fun CloseButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    tint: Color = TayAppTheme.colors.icon
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(Icon_Size)
    ) {
        Icon(
            imageVector = TayIcons.close,
            contentDescription = null,
            tint = tint
        )
    }
}

@Composable
fun CancelButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    tint: Color = lm_gray200
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(Icon_Size)
    ) {
        Icon(
            imageVector = Icons.Filled.Cancel,
            contentDescription = null,
            tint = tint
        )
    }
}