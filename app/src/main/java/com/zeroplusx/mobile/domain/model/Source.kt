package com.zeroplusx.mobile.domain.model

class Source(
    val id: String,
    val title: String,
    val description: String,
    val url: String
) {
    init {
        check(id.isNotEmpty()) { "id of source can't be empty " }
    }
}
