package com.todayeouido.tayapp.presentation.screens.profile

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatSize
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todayeouido.tayapp.presentation.components.CardProfileListItemWithNext
import com.todayeouido.tayapp.presentation.components.SettingModeDialog
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.viewmodels.ProfileViewModel
import com.todayeouido.tayapp.utils.TextSize
import com.todayeouido.tayapp.utils.mutableSize

@Composable
fun ProfileVisibility(
    upPress: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var value by remember { UserState.textSize }
    var dialogVisible by remember { mutableStateOf(false) }
    val viewModel = hiltViewModel<ProfileViewModel>()

    val temp = remember { UserState.mode }

    SettingModeDialog(dialogVisible, {
        viewModel.saveThemeMode(it)
        dialogVisible = !dialogVisible
    }) {
        UserState.mode = temp
        dialogVisible = !dialogVisible
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "앱 정보") {
            viewModel.saveTextSize(UserState.textSize.value)
            upPress()
        }
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithNext(
                icon = Icons.Outlined.LightMode,
                text = "모드",
                subtext = UserState.mode,
                onClick = { dialogVisible = !dialogVisible }
            )


            CardProfileListItemWithNext(
                icon = Icons.Outlined.FormatSize,
                text = "글자 크기",
                subtext = TextSize,
                onClick = {
                    expanded = !expanded
                }
            )
            AnimatedVisibility(visible = expanded) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Slider(
                        value = value,
                        onValueChange = {
                            UserState.textSize.value = it
                            value = it
                        },
                        valueRange = 0.875f..1.25f,
                        steps = 2,
                        modifier = Modifier.background(
                            TayAppTheme.colors.bodyText
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "aaAA가가나나다다", fontSize = 14.mutableSize)
                }
            }
        }
    }

    BackHandler {
        viewModel.saveTextSize(UserState.textSize.value)
        upPress()
    }
}

