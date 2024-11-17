package com.hiebeler.loopy.data.remote.dto

import com.hiebeler.loopy.domain.model.MetaAccount

data class MetaAccountDto(
    val followed_by: Boolean, val following: Boolean
) : DtoInterface<MetaAccount> {
    override fun toModel(): MetaAccount {
        return MetaAccount(
            following = following, followedBy = followed_by
        )
    }
}