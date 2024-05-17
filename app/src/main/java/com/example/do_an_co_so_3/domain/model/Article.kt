package com.example.do_an_co_so_3.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    val userId: String
) : Parcelable {
    constructor() : this("", "", "", "", Source("", ""), "", "", "", "")
}