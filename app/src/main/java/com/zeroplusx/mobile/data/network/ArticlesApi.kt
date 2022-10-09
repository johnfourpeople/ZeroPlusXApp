package com.zeroplusx.mobile.data.network

import com.zeroplusx.mobile.data.model.ArticleListResponseDto
import com.zeroplusx.mobile.data.model.SourcesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesApi {

    @GET("/v2/top-headlines/sources")
    suspend fun getSources(): SourcesResponseDto

    @GET("/v2/everything?pageSize=10")
    suspend fun getArticles(
        @Query("sources") sourceId: String,
        @Query("page") page: Int,
    ): ArticleListResponseDto
}
