package com.zeroplusx.mobile.ui.sources.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeroplusx.mobile.domain.interactor.SourcesInteractor
import com.zeroplusx.mobile.ui.sources.viewState.SourcesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(interactor: SourcesInteractor) : ViewModel() {

    private val _sourcesState = MutableStateFlow<SourcesViewState>(SourcesViewState.Loading)
    val sourcesState: StateFlow<SourcesViewState>
        get() = _sourcesState

    init {
        viewModelScope.launch {
            kotlin.runCatching { interactor.getSources() }
                .onSuccess { _sourcesState.value = SourcesViewState.Sources(it) }
                .onFailure { throwable ->
                    if (throwable is CancellationException) {
                        throw throwable
                    }
                    _sourcesState.value = SourcesViewState.Error(throwable)
                }
        }
    }
}
