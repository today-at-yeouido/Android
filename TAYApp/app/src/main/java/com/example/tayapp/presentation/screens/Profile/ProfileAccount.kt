package com.example.tayapp.presentation.screens.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.BadgePill
import com.example.tayapp.presentation.components.CardProfileListItem
import com.example.tayapp.presentation.components.CardProfileListItemWithNext
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.navigation.ProfileDestination
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun ProfileAccount(
    navController: NavController,
    upPress: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TayTopAppBarWithBack(string = "계정", upPress)
        Column(
            modifier = Modifier.padding(KeyLine)
        ) {
            CardProfileListItemWithNext(
                icon = Icons.Outlined.FavoriteBorder,
                text = "관심분야 설정",
                onClick = {navController.navigate(ProfileDestination.FAVORITE)}
            )
            CardProfileListItem(
                icon = Icons.Outlined.AttachMoney,
                text = "관심분야 설정"
            ){
                BadgePill(text = "준비 중")
            }
        }
    }
}

