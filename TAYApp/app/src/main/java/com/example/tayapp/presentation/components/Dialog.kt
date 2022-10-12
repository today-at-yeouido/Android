package com.example.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.utils.ThemeConstants.DARK
import com.example.tayapp.utils.ThemeConstants.LIGHT
import com.example.tayapp.utils.ThemeConstants.SYSTEM


@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        content()
    }
}

@Composable
fun SettingModeDialog(
    visible: Boolean,
    setMode: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (visible) {
        CustomAlertDialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = TayAppTheme.colors.background)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "모드",
                    color = TayAppTheme.colors.bodyText,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                RadioRow(SYSTEM)
                RadioRow(LIGHT)
                RadioRow(DARK)
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TayButton(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier.size(ButtonSmallWidth, ButtonSmallHeight),
                        backgroundColor = TayAppTheme.colors.layer2,
                        contentColor = TayAppTheme.colors.bodyText
                    ) {
                        Text(text = "취소", style = TayAppTheme.typo.typography.button)
                    }
                    TayButton(
                        onClick = { setMode(UserState.mode) },
                        modifier = Modifier.size(ButtonSmallWidth, ButtonSmallHeight),
                        backgroundColor = TayAppTheme.colors.bodyText,
                        contentColor = TayAppTheme.colors.background
                    ) {
                        Text(text = "확인", style = TayAppTheme.typo.typography.button)
                    }
                }
            }
        }
    }
}

@Composable
private fun RadioRow(mode: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        RadioButton(
            selected = UserState.mode == mode,
            onClick = {
                UserState.mode = mode
            }
        )
        Text(
            text = mode,
            color = TayAppTheme.colors.bodyText,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AppFinishNoticeDialog(
    text: String,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    finishApp: () -> Unit,
) {
    if (visible) {
        //onDismissRequest = 뒤로가기를 눌렀을 경우
        CustomAlertDialog(onDismissRequest = { finishApp() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = TayAppTheme.colors.background)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp),
                    text = text,
                    color = TayAppTheme.colors.bodyText,
                    style = TayAppTheme.typo.typography.h1
                )
                Row(modifier = Modifier.align(Alignment.End)) {

                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                finishApp()
                            }
                            .padding(12.dp),
                        text = "Yes",
                        color = TayAppTheme.colors.bodyText,
                        style = TayAppTheme.typo.typography.h3
                    )
                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                onDismissRequest()
                            }
                            .padding(12.dp),
                        text = "No",
                        color = TayAppTheme.colors.bodyText,
                        style = TayAppTheme.typo.typography.h3
                    )
                }
            }
        }
    }
}

@Composable
fun LogOutNoticeDialog(
    text: String,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    finishApp: () -> Unit,
) {
    if (visible) {
        //onDismissRequest = 뒤로가기를 눌렀을 경우
        CustomAlertDialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = TayAppTheme.colors.background)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp),
                    text = text,
                    color = TayAppTheme.colors.bodyText,
                    style = TayAppTheme.typo.typography.h1
                )
                Row(modifier = Modifier.align(Alignment.End)) {

                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                finishApp()
                            }
                            .padding(12.dp),
                        text = "Yes",
                        color = TayAppTheme.colors.bodyText,
                        style = TayAppTheme.typo.typography.h3
                    )
                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                onDismissRequest()
                            }
                            .padding(12.dp),
                        text = "No",
                        color = TayAppTheme.colors.bodyText,
                        style = TayAppTheme.typo.typography.h3
                    )
                }
            }
        }
    }
}