package com.example.tayapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray050
import com.example.tayapp.presentation.ui.theme.lm_gray400
import com.example.tayapp.presentation.utils.BackButton
import com.example.tayapp.presentation.utils.BookmarkButton
import com.example.tayapp.presentation.utils.CancelButton
import com.example.tayapp.presentation.utils.SearchButton
import com.example.tayapp.utils.textDp


object TopAppBarValue {
    val AppBarHeignt = 50.dp
    val VerticalPadding = 3.dp
    val HorizontalPadding = 16.dp
}

@Composable
fun TayTopAppBar(string: String = "스크랩") {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(TopAppBarValue.AppBarHeignt)

    ) {
        TopBarTitle(
            string = string,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TayTopAppBarWithBack(
    string: String,
    upPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                vertical = TopAppBarValue.VerticalPadding,
                horizontal = TopAppBarValue.HorizontalPadding
            )
            .fillMaxWidth()
            .height(TopAppBarValue.AppBarHeignt)

    ) {
        BackButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = upPress
        )
        TopBarTitle(
            string = string,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TayTopAppBarWithScrap(
    string: String,
    upPress: () -> Unit,
    onClickScrap: () -> Unit
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                vertical = TopAppBarValue.VerticalPadding,
                horizontal = TopAppBarValue.HorizontalPadding
            )
            .fillMaxWidth()
            .height(TopAppBarValue.AppBarHeignt)

    ) {
        BackButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = upPress
        )
        TopBarTitle(
            string = string,
            modifier = Modifier.align(Alignment.Center)
        )
        BookmarkButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            isBookmarked = false,
            onClick = onClickScrap
        )

    }
}

@Composable
fun TayTopAppBarSearch(
    saveQuery: () -> Unit,
    onSearchClick: () -> Unit,
    onCloseClick:() -> Unit,
    onChangeQuery:(String) -> Unit,
    queryValue: String,
    upPress: () -> Unit = {}
) {
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

    ) {

        val focusManager = LocalFocusManager.current


        Image(
            modifier = Modifier.size(26.dp).clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.ic_tay_logo_app),
            contentDescription = "main_title_image"
        )


        Box(
            modifier = Modifier.weight(1f)
        ) {
            TextField(
                placeholder = { Text(text = "법안 검색", fontSize = 14.textDp, color = lm_gray400) },
                value = queryValue,
                onValueChange = {onChangeQuery(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lm_gray050),
                textStyle = TextStyle(fontSize = 14.textDp),
                shape = RoundedCornerShape(8.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSearchClick()
                        saveQuery()
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done )
            )

            if(queryValue!=""){
                CancelButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onCloseClick() }
                )
            }
        }

        SearchButton(onClick = {
            onSearchClick()
            saveQuery()
            focusManager.clearFocus()
        })
    }
}

@Composable
private fun TopBarTitle(
    string: String,
    modifier: Modifier = Modifier
) {
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
private fun TopBarPreview() {
    TayAppTheme {
        TayTopAppBar()
    }
}

@Preview
@Composable
private fun BackTopBarPreview() {
    TayAppTheme() {
        //TayTopAppBarWithBack(string = "스크랩")
    }
}

@Preview
@Composable
private fun BookmarkTopBarPreview() {
    TayAppTheme() {
        //TayTopAppBarWithScrap(string = "스크랩")
    }
}
