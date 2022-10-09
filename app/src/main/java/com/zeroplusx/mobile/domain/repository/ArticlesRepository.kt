package com.zeroplusx.mobile.domain.repository

import com.zeroplusx.mobile.domain.model.Article
import com.zeroplusx.mobile.domain.model.Source

interface ArticlesRepository {

    suspend fun getArticles(source: Source, page: Int): List<Article>
}
