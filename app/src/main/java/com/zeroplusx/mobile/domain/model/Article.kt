package com.zeroplusx.mobile.domain.model

import java.util.*

data class Article(
    val title: String,
    val description: String,
    val thumbnail: String?,
    val author: String,
    val publishedDay: Date?
)
