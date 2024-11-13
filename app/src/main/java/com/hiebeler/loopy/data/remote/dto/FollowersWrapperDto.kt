package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.FollowersWrapper

data class FollowersWrapperDto(
    @SerializedName("data") val data: List<AccountDto>,
    @SerializedName("links") val links: LinksDto,
    @SerializedName("meta") val meta: MetaDto
) : DtoInterface<FollowersWrapper> {
    override fun toModel(): FollowersWrapper {
        return FollowersWrapper(
            data = data.map { it.toModel() },
            links = links.toModel(),
            meta = meta.toModel()
        )
    }
}