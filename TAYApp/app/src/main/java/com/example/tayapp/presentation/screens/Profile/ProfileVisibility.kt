package com.example.tayapp.presentation.screens.Profile

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.CardProfileListItemWithNext
import com.example.tayapp.presentation.components.SettingModeDialog
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.viewmodels.ProfileViewModel
import com.example.tayapp.utils.mutableSize
import com.example.tayapp.utils.textSize

@Composable
fun ProfileVisibility(
    upPress: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf(1f) }
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
        TayTopAppBarWithBack(string = "앱 정보", upPress)
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithNext(
                icon = Icons.Outlined.LightMode,
                text = "모드",
                subtext = "시스템",
                onClick = { dialogVisible = !dialogVisible }
            )


            CardProfileListItemWithNext(
                icon = Icons.Outlined.FormatSize,
                text = "글자 크기",
                subtext = "보통",
                onClick = {
                    expanded = !expanded
                }
            )
            AnimatedVisibility(visible = expanded) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Slider(
                        value = value,
                        onValueChange = {
                            textSize.value = it.toDouble()
                            value = it
                        },
                        valueRange = 0.5f..2f,
                        steps = 3,
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
}

