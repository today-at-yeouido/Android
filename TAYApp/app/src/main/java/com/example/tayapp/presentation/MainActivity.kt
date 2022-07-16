package com.example.tayapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.TAYAppTheme
import com.example.tayapp.presentation.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TAYAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = TAYAppTheme.colors.border
                ) {
                    Text(
                        text = "Hi",
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.fillMaxSize(),
                        color = TAYAppTheme.colors.primary
                    )
                }
            }
        }
    }
}
