package com.zeroplusx.mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
class SourceDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val url: String?,
)
