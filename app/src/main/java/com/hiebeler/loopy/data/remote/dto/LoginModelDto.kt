package com.hiebeler.loopy.data.remote.dto

import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.model.LoginModel

data class LoginModelDto(
    val auth_token: String
) : DtoInterface<LoginModel> {
    override fun toModel(): LoginModel {
        return LoginModel(
            authToken = auth_token
        )
    }
}