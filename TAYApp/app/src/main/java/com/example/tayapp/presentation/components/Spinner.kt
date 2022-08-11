package com.example.tayapp.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.lm_gray075
import com.example.tayapp.presentation.ui.theme.lm_gray400
import com.example.tayapp.presentation.ui.theme.lm_gray600
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.utils.textDp

@Composable
fun Spinner(){
    /** 인자로 받아야 함 */
    var isDropDownMenuExpanded by  remember { mutableStateOf(false) }
    Column {
        Button(onClick = { isDropDownMenuExpanded = true }, modifier = Modifier.background(
            lm_gray075, RoundedCornerShape(8.dp)
        )) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon( imageVector = TayIcons.event, contentDescription = null, modifier = Modifier.size(20.dp),
                    tint = lm_gray600
                )
                Text("정확도순", color = lm_gray600, fontSize = 14.textDp)
                Icon( imageVector = TayIcons.expand_more, contentDescription = null, modifier = Modifier.size(26.dp),
                    tint = lm_gray400
                )
            }
        }
        DropdownMenu(expanded = isDropDownMenuExpanded, onDismissRequest = { isDropDownMenuExpanded = false },
            modifier = Modifier.wrapContentSize()) {
            DropdownMenuItem(onClick = { Log.d("##88", "Hi") }) {
                Text(text = "Hi", color = Color.Black)
            }
            DropdownMenuItem(onClick = { Log.d("##88", "Bye") }) {
                Text(text = "Bye", color = Color.Black)
            }
            DropdownMenuItem(onClick = { Log.d("##88", "Good") }) {
                Text(text = "Good", color = Color.Black)
            }
        }
    }
}