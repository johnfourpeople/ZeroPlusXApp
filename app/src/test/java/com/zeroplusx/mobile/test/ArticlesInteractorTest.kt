package com.zeroplusx.mobile.test

import com.zeroplusx.mobile.domain.interactor.ArticlesInteractor
import com.zeroplusx.mobile.repository.TestArticlesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope

import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesInteractorTest {

    private val coroutineScope = TestScope()
    private val repository = TestArticlesRepository()

    @Test
    fun shouldLoadFirstPage() {
        val repository = TestArticlesRepository()
        val articlesInteractor = ArticlesInteractor(repository)
        val articlesModel = articlesInteractor.getArticlesModel(TestArticlesRepository.successInput, coroutineScope)
        val loading = articlesModel.state.value
        assertIs<ArticlesInteractor.State.Loading>(loading)
        assertEquals(emptyList(), loading.articles)
        assertEquals(1, loading.page)
        coroutineScope.advanceUntilIdle()
        val loaded = articlesModel.state.value
        assertIs<ArticlesInteractor.State.Idle>(loaded)
        assertEquals(TestArticlesRepository.successResponse, loaded.articles)
        assertEquals(1, loaded.page)
        assertEquals(TestArticlesRepository.successInput to 1, repository.lastCalledParamters)
    }

    @Test
    fun shouldReturnError() {
        val articlesInteractor = ArticlesInteractor(repository)
        val articlesModel = articlesInteractor.getArticlesModel(TestArticlesRepository.errorInput, coroutineScope)
        coroutineScope.advanceUntilIdle()
        val error = articlesModel.state.value
        assertIs<ArticlesInteractor.State.Error>(error)
        assertEquals(TestArticlesRepository.errorResponse, error.error)
        assertEquals(1, error.page)
        assertEquals(TestArticlesRepository.errorInput to 1, repository.lastCalledParamters)
    }
}
