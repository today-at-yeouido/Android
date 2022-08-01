package com.example.tayapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray050
import com.example.tayapp.presentation.utils.BackButton
import com.example.tayapp.presentation.utils.BookmarkButton
import com.example.tayapp.presentation.utils.SearchButton


object TopAppBarValue {
    val AppBarHeignt = 50.dp
    val VerticalPadding = 3.dp
    val HorizontalPadding = 16.dp
}

@Composable
fun TayTopAppBar(string: String = "스크랩"){
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(TopAppBarValue.AppBarHeignt)

    ){
        TopBarTitle(
            string = string,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TayTopAppBarWithBack(string: String){
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                vertical = TopAppBarValue.VerticalPadding,
                horizontal = TopAppBarValue.HorizontalPadding
            )
            .fillMaxWidth()
            .height(TopAppBarValue.AppBarHeignt)

    ){
        BackButton(
            modifier = Modifier.align(Alignment.CenterStart)
        )
        TopBarTitle(
            string = string,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TayTopAppBarWithScrap(string: String){
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                vertical = TopAppBarValue.VerticalPadding,
                horizontal = TopAppBarValue.HorizontalPadding
            )
            .fillMaxWidth()
            .height(TopAppBarValue.AppBarHeignt)

    ){
        BackButton(
            modifier = Modifier.align(Alignment.CenterStart)
        )
        TopBarTitle(
            string = string,
            modifier = Modifier.align(Alignment.Center)
        )
        BookmarkButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            isBookmarked = false,
            onClick = { /*TODO*/ }
        )

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TayTopAppBarSearch(
    query: String = "",
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
){
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                vertical = TopAppBarValue.VerticalPadding,
                horizontal = TopAppBarValue.HorizontalPadding
            )
            .fillMaxWidth()
            .height(TopAppBarValue.AppBarHeignt),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ){
        BackButton()

        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        Surface(
            color = lm_gray050,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .fillMaxWidth()
        ) {

        }

        SearchButton()

    }
}

@Composable
private fun TopBarTitle(
    string: String,
    modifier: Modifier = Modifier
){
    Text(
        "$string",
        modifier = modifier,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )

}


@Preview
@Composable
private fun TopBarPreview(){
    TayAppTheme {
        TayTopAppBar()
    }
}

@Preview
@Composable
private fun BackTopBarPreview(){
    TayAppTheme() {
        TayTopAppBarWithBack(string = "스크랩")
    }
}

@Preview
@Composable
private fun BookmarkTopBarPreview(){
    TayAppTheme() {
        TayTopAppBarWithScrap(string = "스크랩")
    }
}

@Preview
@Composable
private fun SearchTopBarPreview() {
    TayAppTheme() {
        TayTopAppBarSearch(
            query = "",
            onQueryChange = {},
            onSearchFocusChange = {})
    }
}