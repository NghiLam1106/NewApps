package com.example.do_an_co_so_3.domain.usercase.news

import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteArticle @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator suspend fun invoke(article: Article){
        newsRepository.deleteArticle(article = article)
    }

}