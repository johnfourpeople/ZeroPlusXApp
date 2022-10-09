package com.zeroplusx.mobile.domain.interactor

import com.zeroplusx.mobile.domain.model.Article
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.domain.repository.ArticlesRepository
import com.zeroplusx.mobile.domain.runCatchingWithoutCancellation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesInteractor @Inject constructor(private val repository: ArticlesRepository) {

    fun getArticlesModel(source: Source, coroutineScope: CoroutineScope): Model {
        val startState = State.Loading(emptyList(), 1)
        val mutableStateFlow = MutableStateFlow<State>(startState)
        var loadingJob: Job = coroutineScope.launch {
            runCatchingWithoutCancellation {
                repository.getArticles(source, startState.page)
            }
                .onSuccess { mutableStateFlow.value = State.Idle(it, startState.page) }
                .onFailure { mutableStateFlow.value = State.Error(startState.articles, startState.page, it) }
        }

        return Model(
            state = mutableStateFlow.asStateFlow(),
            nextPage = {
                val currentState = mutableStateFlow.value
                if (currentState is State.Idle && !loadingJob.isActive) {
                    val newPage = currentState.page + 1
                    mutableStateFlow.value = State.Loading(currentState.articles, newPage)
                    loadingJob = coroutineScope.launch {
                        runCatchingWithoutCancellation { repository.getArticles(source, newPage) }
                            .onSuccess { mutableStateFlow.value = State.Idle(currentState.articles + it, newPage) }
                            .onFailure { mutableStateFlow.value = State.Error(currentState.articles, newPage, it) }
                    }
                }
            },
            retry = {
                val currentState = mutableStateFlow.value
                if (currentState is State.Error && !loadingJob.isActive) {
                    val page = currentState.page
                    mutableStateFlow.value = State.Loading(currentState.articles, page)
                    loadingJob = coroutineScope.launch {
                        runCatchingWithoutCancellation { repository.getArticles(source, page) }
                            .onSuccess { mutableStateFlow.value = State.Idle(currentState.articles + it, page) }
                            .onFailure { mutableStateFlow.value = State.Error(currentState.articles, page, it) }
                    }
                }
            }
        )
    }

    class Model(
        val state: StateFlow<State>,
        val nextPage: () -> Unit,
        val retry: () -> Unit,
    )

    sealed class State(val articles: List<Article>, val page: Int) {
        class Idle(articles: List<Article>, page: Int) : State(articles, page)
        class Loading(articles: List<Article>, page: Int) : State(articles, page)
        class Error(articles: List<Article>, page: Int, val error: Throwable) : State(articles, page)
    }
}
