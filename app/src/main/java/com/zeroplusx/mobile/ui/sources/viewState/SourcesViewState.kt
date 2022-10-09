package com.zeroplusx.mobile.ui.sources.viewState

import com.zeroplusx.mobile.domain.model.Source

sealed class SourcesViewState {

    object Loading : SourcesViewState()
    class Error(val error: Throwable) : SourcesViewState()
    class Sources(val articles: List<Source>) : SourcesViewState()
}
