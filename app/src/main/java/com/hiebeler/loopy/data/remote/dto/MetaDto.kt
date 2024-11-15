package com.hiebeler.loopy.data.remote.dto

import com.hiebeler.loopy.domain.model.Links
import com.hiebeler.loopy.domain.model.Meta

data class MetaDto(
    val next_cursor: String?,
    val path: String,
    val per_page: Int,
    val prev_cursor: String?
) : DtoInterface<Meta> {
    override fun toModel(): Meta {
        return Meta(
            nextCursor = next_cursor,
            path = path,
            perPage = per_page,
            prevCursor = prev_cursor
        )
    }
}