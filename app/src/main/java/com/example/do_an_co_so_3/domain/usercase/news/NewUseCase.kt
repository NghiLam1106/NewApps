package com.example.do_an_co_so_3.domain.usercase.news

data class NewsUseCase(
    val getNews: GetNews,
    val searchNews: SearchNews,
    val getSavedArticleUseCase: GetSavedArticle,
    val deleteArticleUseCase: DeleteArticle,
    val addArticleUseCase: AddArticle,
)
