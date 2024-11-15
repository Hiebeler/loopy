package com.hiebeler.loopy.data.remote.dto

import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.model.Links

data class LinksDto(
    val first: Any?,
    val last: Any?,
    val next: String?,
    val prev: String?
) : DtoInterface<Links> {
    override fun toModel(): Links {
        return Links(
            next = next,
            prev = prev
        )
    }
}