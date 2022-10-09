package com.zeroplusx.mobile.ui.articles.viewState

import com.zeroplusx.mobile.domain.model.Article

sealed class ArticleListViewState {

    object Loading : ArticleListViewState()
    class Error(val error: Throwable) : ArticleListViewState()
    class Articles(val articles: List<Article>) : ArticleListViewState()
}
