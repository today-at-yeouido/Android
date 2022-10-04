package com.example.tayapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
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
    title: String,
    upPress: () -> Unit,
    onClickScrap: () -> Unit,
    isBookMarked: Boolean
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
            string = title,
            modifier = Modifier.align(Alignment.Center)
        )
        BookmarkButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            isBookmarked = isBookMarked,
            onClick = { onClickScrap() }
        )

    }
}

@Composable
fun TayTopAppBarSearch(
    saveQuery: () -> Unit,
    onSearchClick: () -> Unit,
    onCloseClick: () -> Unit,
    onChangeQuery: (String) -> Unit,
    queryValue: String,
    autoComplete: List<String>,
    onAutoCompleteClick: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    isFocused: Boolean,
    searching: Boolean
) {

    val focusManager = LocalFocusManager.current

    Column() {
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


            if(searching) {
                BackButton(
                    modifier = Modifier.size(26.dp),
                    onClick = {onCloseClick(); focusManager.clearFocus()}
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(id = R.drawable.ic_tay_logo_app),
                    contentDescription = "main_title_image"
                )
            }


            Box(
                modifier = Modifier.weight(1f)
            ) {
                TayEditText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(
                            color = TayAppTheme.colors.layer1,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .onFocusChanged { onFocusChange(it.isFocused) }
                        .padding(0.dp),
                    value = queryValue,
                    onValueChange = onChangeQuery,
                    placeholder = {
                        Text(
                            "법안 검색",
                            color = TayAppTheme.colors.disableText,
                            fontSize = 13.sp
                        ) },
                    focusManager = focusManager
                )
                if (queryValue != "") {
                    CancelButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { onCloseClick(); focusManager.clearFocus() }
                    )
                }
            }

            SearchButton(onClick = {
                onSearchClick()
                saveQuery()
                focusManager.clearFocus()
            })
        }
        if(isFocused){
            LazyColumn(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            ){
                items(autoComplete){ it ->
                    AutoCompleteItem(onItemClick = { onAutoCompleteClick(it); focusManager.clearFocus()}, text = it, keyword = queryValue)
                }
            }
        }
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
        color = TayAppTheme.colors.headText,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )

}

@Composable
private fun AutoCompleteItem(
    onItemClick: () -> Unit,
    text: String,
    keyword: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = KeyLine)
            .clickable(onClick = onItemClick)
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = TayAppTheme.colors.icon
        )

        Spacer(modifier = Modifier.size(8.dp))

        val startIndex = text.indexOf(keyword)

        if (keyword != "" && startIndex != -1) {
            Text(
                text = buildAnnotatedString {

                    if (startIndex == 0) {
                        withStyle(style = SpanStyle(color = TayAppTheme.colors.primary)) {
                            append(keyword)
                        }
                        append(text.substring(keyword.length, text.length))
                    } else if(startIndex != -1){
                        append(text.substring(0, startIndex))

                        withStyle(style = SpanStyle(color = TayAppTheme.colors.primary)) {
                            append(keyword)
                        }
                        append(text.substring(startIndex + keyword.length, text.length))
                    }
                },
                color = TayAppTheme.colors.bodyText,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                maxLines = 1
            )
        } else {
            Text(text, fontWeight = FontWeight.Normal, fontSize = 14.sp, maxLines = 1)
        }

    }

}
