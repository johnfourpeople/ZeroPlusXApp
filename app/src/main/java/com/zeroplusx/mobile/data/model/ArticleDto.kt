package com.zeroplusx.mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
class ArticleDto(
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val author: String?,
    val publishedAt: String?
)
