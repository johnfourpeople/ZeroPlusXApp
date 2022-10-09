package com.zeroplusx.mobile.data.repository

import com.zeroplusx.mobile.data.network.ArticlesApi
import com.zeroplusx.mobile.domain.model.Article
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.domain.repository.ArticlesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiArticlesRepository @Inject constructor(private val api: ArticlesApi) : ArticlesRepository {

    override suspend fun getArticles(source: Source): List<Article> {
        return api.getArticles(source.id).articles.map { dto ->
            Article(
                title = dto.title ?: "",
                description = dto.description ?: "",
                thumbnail = dto.urlToImage ?: "",
                author = dto.author ?: "",
                publishedDay = dto.publishedAt ?: ""
            )
        }
    }
}
