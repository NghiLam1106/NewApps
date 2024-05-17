package com.example.do_an_co_so_3.presentation.save_news

import com.example.do_an_co_so_3.domain.model.Article


data class SaveNewsState (
    val articles: List<Article> = emptyList()
)

