package com.example.do_an_co_so_3.domain.usercase.news

import androidx.paging.PagingData
import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }
}