package com.example.do_an_co_so_3.presentation.details

import com.example.do_an_co_so_3.domain.model.Article

sealed class DetailsEvent {

    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    data object RemoveSideEffect : DetailsEvent()
}