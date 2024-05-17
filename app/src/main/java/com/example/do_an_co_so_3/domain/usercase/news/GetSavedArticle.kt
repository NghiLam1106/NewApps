package com.example.do_an_co_so_3.domain.usercase.news

import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.domain.repository.NewsRepository
import javax.inject.Inject

class GetSavedArticle @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(url: String): Article?{
        return newsRepository.getArticle(url = url)
    }

}