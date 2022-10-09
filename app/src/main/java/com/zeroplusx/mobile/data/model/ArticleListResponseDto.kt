package com.zeroplusx.mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
class ArticleListResponseDto(
    val articles: List<ArticleDto>
)
