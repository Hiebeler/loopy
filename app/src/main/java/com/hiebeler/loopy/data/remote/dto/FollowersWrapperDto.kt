package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Wrapper

data class FollowersWrapperDto(
    @SerializedName("data") val data: List<AccountDto>,
    @SerializedName("links") val links: LinksDto,
    @SerializedName("meta") val meta: MetaDto
) : DtoInterface<Wrapper<Account>> {
    override fun toModel(): Wrapper<Account> {
        return Wrapper(
            data = data.map { it.toModel() },
            previousCursor = meta.toModel().prevCursor,
            nextCursor = meta.toModel().nextCursor
        )
    }
}