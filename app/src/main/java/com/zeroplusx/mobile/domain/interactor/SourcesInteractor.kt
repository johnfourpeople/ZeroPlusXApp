package com.zeroplusx.mobile.domain.interactor

import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.domain.repository.SourcesRepository
import javax.inject.Inject

class SourcesInteractor @Inject constructor(private val repository: SourcesRepository) {

    suspend fun getSources(): List<Source> {
        return repository.getSources()
    }
}
