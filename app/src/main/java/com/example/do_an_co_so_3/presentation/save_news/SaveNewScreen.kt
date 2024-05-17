package com.example.do_an_co_so_3.presentation.save_news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.do_an_co_so_3.R
import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.presentation.Dimens
import com.example.do_an_co_so_3.presentation.common.ArticlesListFromData

@Composable
fun SaveNewScreen(
    state: SaveNewsState,
    navigateToDetails: (Article) -> Unit

) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                top = Dimens.MediumPadding1,
                start = Dimens.MediumPadding1,
                end = Dimens.MediumPadding1
            )
    ) {

        Text(
            text = "Lưu trữ bài báo",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(
                id = R.color.text_title
            )
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding1))
        ArticlesListFromData(articles = state.articles, onClick = navigateToDetails)
    }
}