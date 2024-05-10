package com.example.do_an_co_so_3.domain.repository

import androidx.paging.PagingData
import com.example.do_an_co_so_3.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>

}