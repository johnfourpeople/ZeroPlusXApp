package com.zeroplusx.mobile.repository

import com.zeroplusx.mobile.domain.model.Article
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.domain.repository.ArticlesRepository
import java.lang.RuntimeException
import kotlin.IllegalArgumentException

class TestArticlesRepository : ArticlesRepository {
    var lastCalledParamters: Pair<Source, Int>? = null

    override suspend fun getArticles(source: Source, page: Int): List<Article> {
        lastCalledParamters = source to page
        return when (source) {
            successInput -> successResponse
            errorInput -> throw errorResponse
            else -> throw IllegalArgumentException("Input is not supported by mock repo")
        }
    }

    companion object {
        val successInput = Source("1", "1", "1", "1")
        val successResponse = listOf(
            Article(
                title = "title",
                description = "description",
                thumbnail = "thumbnail",
                author = "author",
                publishedDay = null
            )
        )
        val errorInput = Source("1", "1", "1", "1")
        val errorResponse = RuntimeException("Mocked error response")
    }
}
