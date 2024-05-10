package com.example.do_an_co_so_3.data.remote

import com.example.do_an_co_so_3.data.remote.dto.NewsResponse
import com.example.do_an_co_so_3.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

//    @GET("news")
    @GET("everything")
    suspend fun getNews (
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse



}