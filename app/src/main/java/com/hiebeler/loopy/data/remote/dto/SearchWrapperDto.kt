package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.NotificationsWrapper
import com.hiebeler.loopy.domain.model.SearchWrapper

data class SearchWrapperDto(
    @SerializedName("data") val data: List<AccountDto>,
    @SerializedName("links") val links: LinksDto,
    @SerializedName("meta") val meta: MetaDto
) : DtoInterface<SearchWrapper> {
    override fun toModel(): SearchWrapper {
        return SearchWrapper(
            users = data.map { it.toModel() },
            nextCursor = meta.toModel().nextCursor ?: "",
            previousCursor = meta.toModel().prevCursor ?: ""
        )
    }
}