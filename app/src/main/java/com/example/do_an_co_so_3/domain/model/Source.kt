package com.example.do_an_co_so_3.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String,
    val name: String
) : Parcelable {
    constructor() : this("", "")
}