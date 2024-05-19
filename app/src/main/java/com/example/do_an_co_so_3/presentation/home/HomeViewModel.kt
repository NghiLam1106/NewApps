package com.example.do_an_co_so_3.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.do_an_co_so_3.domain.usercase.news.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    val news = newsUseCase.getNews(
        sources = listOf("bbc-news", "la-nacion", "ign", "die-zeit", "fortune", "cnn")
    ).cachedIn(viewModelScope)

}