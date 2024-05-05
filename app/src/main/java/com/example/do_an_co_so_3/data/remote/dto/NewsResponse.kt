package com.example.do_an_co_so_3.data.remote.dto

import com.example.do_an_co_so_3.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)