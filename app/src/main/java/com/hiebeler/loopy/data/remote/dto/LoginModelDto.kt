package com.hiebeler.loopy.data.remote.dto

import com.hiebeler.loopy.domain.model.LoginModel

data class LoginModelDto(
    val auth_token: String
) : DtoInterface<LoginModel> {
    override fun toModel(): LoginModel {
        return LoginModel(
            authToken = auth_token.substring(auth_token.indexOf('|')+1)
        )
    }
}