package com.example.tayapp.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.lm_gray100
import com.example.tayapp.presentation.ui.theme.lm_gray200
import com.example.tayapp.presentation.ui.theme.lm_primary50

@Composable
fun TayCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier.size(26.dp),
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = lm_primary50,
        uncheckedColor = lm_gray200,
        checkmarkColor = lm_gray100,
    )
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = colors
    )
}