package com.zeroplusx.mobile.domain.interactor

import com.zeroplusx.mobile.domain.model.Article
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.domain.repository.ArticlesRepository
import javax.inject.Inject

class ArticlesInteractor @Inject constructor(private val repository: ArticlesRepository) {

    suspend fun getArticles(source: Source): List<Article> {
        return repository.getArticles(source)
    }
}
