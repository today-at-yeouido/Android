package com.todayeouido.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.utils.ThemeConstants.DARK
import com.todayeouido.tayapp.utils.ThemeConstants.LIGHT
import com.todayeouido.tayapp.utils.ThemeConstants.SYSTEM


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
                        modifier = Modifier
                            .height(ButtonSmallHeight)
                            .fillMaxWidth().
                            border(1.dp, TayAppTheme.colors.border, RoundedCornerShape(8.dp))
                            .weight(1f),
                        backgroundColor = TayAppTheme.colors.background,
                        contentColor = TayAppTheme.colors.bodyText
                    ) {
                        Text(text = "취소", style = TayAppTheme.typo.typography.button)
                    }
                    TayButton(
                        onClick = { setMode(UserState.mode) },
                        modifier = Modifier.height(ButtonSmallHeight).fillMaxWidth().weight(1f),
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
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.clickable { UserState.mode = mode }
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
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = TayAppTheme.colors.background)
                    .padding(vertical = 24.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = text,
                    color = TayAppTheme.colors.bodyText,
                    style = TayAppTheme.typo.typography.body1
                )

                Spacer(modifier = Modifier.size(20.dp))

                Row() {
                    TayButton(
                        onClick = { onDismissRequest() },
                        backgroundColor = TayAppTheme.colors.background,
                        modifier = Modifier
                            .weight(1f)
                            .height(ButtonSmallHeight)
                            .border(1.dp, TayAppTheme.colors.border, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            "돌아가기",
                            style = TayAppTheme.typo.typography.button,
                            color = TayAppTheme.colors.bodyText
                        )
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    TayButton(
                        onClick = { finishApp() },
                        backgroundColor = TayAppTheme.colors.bodyText,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(ButtonSmallHeight)
                    ) {
                        Text("나가기", style = TayAppTheme.typo.typography.button)
                    }
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
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = TayAppTheme.colors.background)
                    .padding(vertical = 24.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = text,
                    color = TayAppTheme.colors.bodyText,
                    style = TayAppTheme.typo.typography.body1
                )

                Spacer(modifier = Modifier.size(20.dp))

                Row(modifier = Modifier.align(Alignment.End)) {

                    TayButton(
                        onClick = { onDismissRequest() },
                        backgroundColor = TayAppTheme.colors.background,
                        modifier = Modifier
                            .weight(1f)
                            .height(ButtonSmallHeight)
                            .border(1.dp, TayAppTheme.colors.border, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            "돌아가기",
                            style = TayAppTheme.typo.typography.button,
                            color = TayAppTheme.colors.bodyText
                        )
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    TayButton(
                        onClick = { finishApp() },
                        backgroundColor = TayAppTheme.colors.bodyText,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(ButtonSmallHeight)
                    ) {
                        Text("로그아웃", style = TayAppTheme.typo.typography.button)
                    }
                }
            }
        }
    }
}

@Composable
fun WithDrawNoticeDialog(
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
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = TayAppTheme.colors.background)
                    .padding(vertical = 24.dp)
                    .padding(horizontal = 16.dp)

            ) {
                Text(
                    modifier = Modifier,
                    text = text,
                    color = TayAppTheme.colors.bodyText,
                    style = TayAppTheme.typo.typography.body1
                )

                Spacer(modifier = Modifier.size(6.dp))


                Text(
                    modifier = Modifier,
                    text = "탈퇴 후 7일 내 같은 계정으로 재가입할 수 없어요.",
                    color = TayAppTheme.colors.subduedText,
                    style = TayAppTheme.typo.typography.body2
                )

                Spacer(modifier = Modifier.size(20.dp))


                Row(modifier = Modifier.fillMaxWidth()) {
                    
                    TayButton(
                        onClick = { onDismissRequest() },
                        backgroundColor = TayAppTheme.colors.background,
                        modifier = Modifier
                            .weight(1f)
                            .height(ButtonSmallHeight)
                            .border(1.dp, TayAppTheme.colors.border, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            "취소",
                            style = TayAppTheme.typo.typography.button,
                            color = TayAppTheme.colors.bodyText
                        )
                    }
                    
                    Spacer(modifier = Modifier.size(10.dp))

                    TayButton(
                        onClick = { finishApp() },
                        backgroundColor = TayAppTheme.colors.danger2,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(ButtonSmallHeight)
                    ) {
                        Text("탈퇴", style = TayAppTheme.typo.typography.button)
                    }
                    
                }
            }
        }
    }
}