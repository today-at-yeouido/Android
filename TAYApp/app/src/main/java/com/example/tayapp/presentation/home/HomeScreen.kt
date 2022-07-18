package com.example.tayapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.components.BottomBarTabs

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Icon(imageVector = BottomBarTabs.HOME.icon, contentDescription = "")
        Icon(imageVector = BottomBarTabs.SCRAP.icon, contentDescription = "")
        Icon(imageVector = BottomBarTabs.REPORT.icon, contentDescription = "")
        Icon(imageVector = BottomBarTabs.PROFILE.icon, contentDescription = "")
    }
}