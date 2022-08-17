package com.example.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tayapp.presentation.ui.theme.TayAppTheme


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
fun EmailVerifyLinkNoticeDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    finishApp: () -> Unit,
) {
    if (visible) {
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
                    text = "종료하시겠습니까~?~?~?~?",
                    style = TayAppTheme.typo.typography.h1
                )
                Row(modifier = Modifier.align(Alignment.End)){

                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                finishApp()
                            }
                            .padding(12.dp),
                        text = "Yes",
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
                        style = TayAppTheme.typo.typography.h3
                    )
                }
            }
        }
    }
}