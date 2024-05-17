package com.example.do_an_co_so_3.domain.usercase.news

import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.domain.repository.NewsRepository
import javax.inject.Inject

class AddArticle @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(article: Article, userId: String){
        newsRepository.addArticle(article = article, userId = userId)
    }
}