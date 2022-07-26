package com.example.tayapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.Shapes
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.utils.Emoij

private object CardUserValue {
    val CardBorderWidth = 1.dp
    val CardItemSpace = 12.dp
    val CardItemHeight = 54.dp
    val CardItemPadding = 8.dp
    val CardEmoijPadding = 7.dp
}


@Composable
fun CardUser(
    modifier: Modifier = Modifier,
    items: List<Int>
){
    Card(
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(KeyLine),
        border = BorderStroke(
            CardUserValue.CardBorderWidth,
            color = TayAppTheme.colors.border
        ),
        backgroundColor = TayAppTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(Card_Inner_Padding),
            verticalArrangement = Arrangement.spacedBy(CardUserValue.CardItemSpace)
        ) {
            CardUserItem()
            CardUserItem()
            CardUserItem()
        }
    }
}

@Composable
fun CardUserItem(
    taglist: List<String> = listOf("과학","누리호"),
    title: String = "가덕도 신공항 건설을 위한 특별법",
    onClick: () -> Unit = {}
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(CardUserValue.CardItemHeight)
            .clickable { onClick }
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = CardUserValue.CardItemPadding)
        ) {
            Row() {
                taglist.forEach{
                    CardUserTagText(tag = it)
                }
            }
            CardUserTitleText(title = title)
        }

        EmoijText()
    }
}

@Composable
fun CardUserTitleText(title: String){
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )
}



@Composable
fun CardUserTagText(tag: String){
    Text(
        "#$tag",
        fontSize = 12.sp
    )
}

@Composable
fun EmoijText(
    emoij: String? = Emoij["해양"]
){
    Text(
        "$emoij",
        modifier = Modifier
            .padding(vertical = CardUserValue.CardEmoijPadding),
        fontSize = 30.sp
    )
}


@Composable
@Preview
private fun PreviewUserSection(){
    TayAppTheme() {
        CardUser(items = listOf(1,2,3,4))
    }
}