package com.zeroplusx.mobile.ui.articles.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zeroplusx.mobile.domain.interactor.ArticlesInteractor
import com.zeroplusx.mobile.domain.model.Source
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow

class ArticlesViewModel @AssistedInject constructor(
    @Assisted source: Source,
    articlesInteractor: ArticlesInteractor
) : ViewModel() {

    private val articlesModel = articlesInteractor.getArticlesModel(source, viewModelScope)
    val articleListState: StateFlow<ArticlesInteractor.State>
        get() = articlesModel.state

    fun onNextPage() {
        articlesModel.nextPage.invoke()
    }

    fun onRetry() {
        articlesModel.retry.invoke()
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
