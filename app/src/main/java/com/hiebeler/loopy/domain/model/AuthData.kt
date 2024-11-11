package com.hiebeler.loopy.domain.model

import com.hiebeler.loopy.common.Constants
import kotlinx.serialization.Serializable

@Serializable
data class AuthData(
    val loginDataList: List<LoginData> = emptyList(), val currentlyLoggedIn: String = ""
)

@Serializable
data class LoginData(
    val version: Constants.AuthVersions = Constants.AuthVersions.V2,
    val accountId: String = "",
    val username: String = "",
    val displayName: String? = null,
    val avatar: String = "",
    val baseUrl: String = "",
    val accessToken: String = "",
)