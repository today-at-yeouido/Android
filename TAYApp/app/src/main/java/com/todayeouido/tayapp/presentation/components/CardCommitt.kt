package com.todayeouido.tayapp.presentation.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.todayeouido.tayapp.R
import com.todayeouido.tayapp.presentation.TayApp
import com.todayeouido.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme

/**
 * 상임위 설명 카드
 * @param
 */
@Composable
fun CardCommitt(selectedCategory: Int?) {

    var context = LocalContext.current as Activity
    var resId =
        context.resources.getIdentifier("committee_${selectedCategory}","string", "com.todayeouido.tayapp")
    var description = context.resources.getString(resId)

    TayCard(
        modifier = Modifier
            .padding(KeyLine)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(Card_Inner_Padding)
        ) {
            Text(
                text = datalist[selectedCategory?: 1][0],
                color = TayAppTheme.colors.headText,
                style = TayAppTheme.typo.typography.h2
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = description,
                color = TayAppTheme.colors.bodyText,
                style = TayAppTheme.typo.typography.body1
            )

        }
    }

}