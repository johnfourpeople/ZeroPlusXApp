package com.zeroplusx.mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
class SourcesResponseDto(
    val sources: List<SourceDto>
)
