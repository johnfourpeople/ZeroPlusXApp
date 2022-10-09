package com.zeroplusx.mobile.data.repository

import com.zeroplusx.mobile.data.network.ArticlesApi
import com.zeroplusx.mobile.domain.model.Article
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.domain.repository.ArticlesRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiArticlesRepository @Inject constructor(private val api: ArticlesApi) : ArticlesRepository {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ROOT)

    override suspend fun getArticles(source: Source, page: Int): List<Article> {
        return api.getArticles(source.id, page).articles.map { dto ->
            Article(
                title = dto.title ?: "",
                description = dto.description ?: "",
                thumbnail = dto.urlToImage,
                author = dto.author ?: "",
                publishedDay = dto.publishedAt?.let { publishedAtString ->
                    kotlin.runCatching { simpleDateFormat.parse(publishedAtString) }.getOrNull()
                }
            )
        }
    }
}
