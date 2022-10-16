package com.todayeouido.tayapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.todayeouido.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.ui.theme.lm_gray600

@Composable
fun CardNews(
    imageURL: String? = "https://yt3.ggpht.com/ytc/AKedOLR5NNn9lbbFQqPkHCTMgvgvCjZi94G2JRU7DjsM=s900-c-k-c0x00ffffff-no-rj",
    title: String = "중대재해처벌법, 두려움을 기회로 바꿔라",
    date: String = "22.07.19",
    press: String = "언론사",
    newsLink: String,
    mUriHandler: UriHandler
) {
    TayCard(
        modifier = Modifier
            .padding(horizontal = KeyLine)
            .height(100.dp)
            .fillMaxWidth(),
        enable = true,
        onClick = { mUriHandler.openUri(newsLink) }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = Card_Inner_Padding)
        ) {

            TayImage(
                imageURL = imageURL,
                modifier = Modifier
                    .width(112.dp)
                    .fillMaxHeight()
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 13.dp)
            ) {
                Text(
                    text = title.replace("\n", ""),
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = TayAppTheme.colors.headText,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 1.4.em,
                    fontSize = 16.sp,
                    maxLines = 2
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = date,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = TayAppTheme.colors.subduedText,
                        maxLines = 2
                    )
                    Text(
                        text = press,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = TayAppTheme.colors.subduedText,
                    )
                }
            }
        }
    }
}

@Composable
fun TayImage(
    imageURL: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}
