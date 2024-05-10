package com.example.do_an_co_so_3.presentation.search

import androidx.paging.PagingData
import com.example.do_an_co_so_3.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)