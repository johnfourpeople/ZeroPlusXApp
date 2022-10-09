package com.zeroplusx.mobile.data.repository

import com.zeroplusx.mobile.data.network.ArticlesApi
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.domain.repository.SourcesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiSourcesRepository @Inject constructor(private val api: ArticlesApi) : SourcesRepository {
    override suspend fun getSources(): List<Source> {
        return api.getSources().sources.mapNotNull { dto ->
            if (dto.id != null) {
                Source(
                    id = dto.id,
                    title = dto.name ?: "",
                    description = dto.description ?: "",
                    url = dto.url ?: "",
                )
            } else {
                null
            }
        }
    }
}
