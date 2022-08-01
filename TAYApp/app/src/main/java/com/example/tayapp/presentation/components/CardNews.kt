package com.example.tayapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray600

@Composable
fun CardNews(
    imageURL: String = "https://yt3.ggpht.com/ytc/AKedOLR5NNn9lbbFQqPkHCTMgvgvCjZi94G2JRU7DjsM=s900-c-k-c0x00ffffff-no-rj",
    title: String = "중대재해처벌법, 두려움을 기회로 바꿔라",
    date: String ="22.07.19",
    press: String = "언론사"
){
    TayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(end = Card_Inner_Padding)
        ){

            TayImage(
                imageURL = imageURL,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(112.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 15.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    maxLines = 2
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = date,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = lm_gray600,
                        maxLines = 2
                    )
                    Text(
                        text = press,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = lm_gray600,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun TayImage(
    imageURL: String,
    modifier: Modifier = Modifier,
){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier

    )
}

@Preview
@Composable
private fun NewsCardPreview(){
    TayAppTheme() {
        CardNews()
    }
}