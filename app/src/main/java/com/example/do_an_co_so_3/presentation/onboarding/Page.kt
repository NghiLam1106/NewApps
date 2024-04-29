package com.example.do_an_co_so_3.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.do_an_co_so_3.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Lorem Ipsum is simple dummy",
        description = "Lorem Ipsum is simple dummy text of the printing and typesetting industry.",
        image = R.drawable.ic_launcher_background
    ),
    Page(
        title = "Lorem Ipsum is simple dummy",
        description = "Lorem Ipsum is simple dummy text of the printing and typesetting industry.",
        image = R.drawable.vananh
    ),
    Page(
        title = "Lorem Ipsum is simple dummy",
        description = "Lorem Ipsum is simple dummy text of the printing and typesetting industry.",
        image = R.drawable.ic_launcher_foreground
    )
)
