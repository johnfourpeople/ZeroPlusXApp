package com.zeroplusx.mobile.ui.listing.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zeroplusx.mobile.domain.interactor.ArticlesInteractor
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.ui.listing.viewState.ArticleListViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticlesViewModel @AssistedInject constructor(
    @Assisted source: Source,
    articlesInteractor: ArticlesInteractor
) : ViewModel() {

    private val _articleListState = MutableStateFlow<ArticleListViewState>(ArticleListViewState.Loading)
    val articleListState: StateFlow<ArticleListViewState>
        get() = _articleListState

    init {
        viewModelScope.launch {
            kotlin.runCatching { articlesInteractor.getArticles(source) }
                .onSuccess { _articleListState.value = ArticleListViewState.Articles(it) }
                .onFailure { throwable ->
                    if (throwable is CancellationException) {
                        throw throwable
                    }
                    _articleListState.value = ArticleListViewState.Error(throwable)
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(source: Source): ArticlesViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            source: Source
        ): ViewModelProvider.Factory {
            val value = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(source) as T
                }
            }
            return value
        }
    }
}
