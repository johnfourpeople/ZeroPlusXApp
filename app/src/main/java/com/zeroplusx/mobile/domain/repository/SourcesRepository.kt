package com.zeroplusx.mobile.domain.repository

import com.zeroplusx.mobile.domain.model.Source

interface SourcesRepository {
    suspend fun getSources(): List<Source>
}
