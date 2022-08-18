package com.example.tayapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons

@Composable
fun CardUserProfile() {
    TayCard() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(Card_Inner_Padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CardUserProfileText()
                TayButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(ButtonSmallWidth)
                        .height(ButtonSmallHeight),
                    backgroundColor = TayAppTheme.colors.background,
                    contentColor = TayAppTheme.colors.headText,
                    border = BorderStroke(1.dp, TayAppTheme.colors.border)
                ) {
                    Text("프로필 수정", style = TayAppTheme.typo.typography.button)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Badge(title = "출석")
                Badge(title = "법안", emoij = "\uD83C\uDF31")
                Badge(title = "뉴스", emoij = "\uD83D\uDDDE️")
            }

            TayButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonMediumHeight),
                backgroundColor = TayAppTheme.colors.background,
                contentColor = TayAppTheme.colors.headText,
                border = BorderStroke(1.dp, TayAppTheme.colors.border)

            ) {
                Text("뱃지 더보기", style = TayAppTheme.typo.typography.button)
                Icon(
                    imageVector = TayIcons.navigate_next,
                    contentDescription = null
                )

            }
        }
    }
}

@Composable
private fun CardUserProfileText(
    name: String = "사용자",
    email: String = "happy@gmail.com"
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            "${UserState.user.id} 님",
            style = TayAppTheme.typo.typography.h2,
            color = TayAppTheme.colors.headText
        )
        Text(
            UserState.user.email,
            fontWeight = FontWeight.Light,
            color = TayAppTheme.colors.headText,
            fontSize = 14.sp
        )
    }
}

/**
 * modifier로 background를 쓰면 크기조절을 못해서 Surface로 준다.
 */
@Composable
fun CardProfilSection(
    icon: ImageVector = TayIcons.setting_outlined,
    title: String = "설정",
    subTitle: String = "보기, 알람",
    onClick: () -> Unit = {}
) {
    TayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        onClick = onClick,
        enable = true
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp)
        ) {
            Box() {
                Surface(
                    color = TayAppTheme.colors.layer2,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.size(38.dp)
                ) {}
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = TayAppTheme.colors.headText,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "$title",
                    fontWeight = FontWeight.Medium,
                    color = TayAppTheme.colors.headText,
                    fontSize = 14.sp
                )
                Text(
                    "$subTitle",
                    fontWeight = FontWeight.Normal,
                    color = TayAppTheme.colors.subduedText,
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = TayIcons.navigate_next,
                contentDescription = null,
                tint = TayAppTheme.colors.disableIcon
            )

        }
    }
}

@Composable
fun CardProfileListItem(
    icon: ImageVector = TayIcons.visibility_outlined,
    text: String = "입력",
    subtext: String = "",
    onClick: () -> Unit = {},
    endComponent: @Composable RowScope. () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            tint = TayAppTheme.colors.bodyText,
            contentDescription = ""
        )
        Text(
            "$text",
            fontWeight = FontWeight.Medium,
            color = TayAppTheme.colors.headText,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            "$subtext",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TayAppTheme.colors.subduedText
        )
        Row(content = endComponent)
    }
}

@Composable
fun CardProfileListItemWithLink(
    icon: ImageVector = TayIcons.visibility_outlined,
    text: String = "입력",
    subtext: String = ""
) {
    CardProfileListItem(
        icon = icon,
        text = text,
        subtext = subtext
    ) {
        Icon(
            imageVector = TayIcons.north_east,
            contentDescription = null,
            tint = TayAppTheme.colors.disableIcon
        )
    }
}

@Composable
fun CardProfileListItemWithNext(
    icon: ImageVector = TayIcons.visibility_outlined,
    text: String = "입력",
    subtext: String = "",
    onClick: () -> Unit = {}
) {
    CardProfileListItem(
        icon = icon,
        text = text,
        subtext = subtext,
        onClick = onClick
    ) {
        Icon(
            imageVector = TayIcons.navigate_next,
            contentDescription = null,
            tint = TayAppTheme.colors.disableIcon
        )
    }
}

@Composable
fun CardProfileListItemWithOutIcon(
    text: String = "입력",
    subtext: String = "asdfjn",
    onClick: () -> Unit = {},
    endComponent: @Composable RowScope. () -> Unit
) {
    CardProfileListItem(
        text = text,
        subtext = subtext,
        endComponent = endComponent,
        onClick = onClick
    )
}

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    emoij: String? = "\uD83D\uDC40",
    title: String = "출석"
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box() {
            Spacer(
                modifier = Modifier
                    .height(60.dp)
                    .width(90.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                TayAppTheme.colors.sub10,
                                TayAppTheme.colors.primary20
                            )
                        ),
                        shape = RoundedCornerShape(topEnd = 60.dp, topStart = 60.dp)
                    )
            )
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$emoij",
                    fontSize = 24.sp
                )
                BadgePill(text = "Lv.1")
            }
        }
        Text(
            text = "$title 뱃지",
            fontWeight = FontWeight.Normal,
            color = TayAppTheme.colors.subduedText,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun BadgePreview() {
    TayAppTheme() {
        Badge()
    }
}

@Composable
@Preview
private fun CardIserPreview() {
    TayAppTheme {
        CardUserProfile()
        //CardProfilSection()
//        CardProfileListItem(){
//            Icon(
//                imageVector = TayIcons.north_east,
//                contentDescription = null,
//                tint = lm_gray300
//            )
//        }
    }
}