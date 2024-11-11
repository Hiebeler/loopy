package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.domain.model.AuthData
import com.hiebeler.loopy.domain.model.LoginData

interface AuthRepository {
    suspend fun addNewLoginData(newLoginData: LoginData)
    suspend fun finishLogin(newLoginData: LoginData, currentlyLoggedIn: String)
    suspend fun updateCurrentUser(accountId: String)
    suspend fun getAuthData(): AuthData
    suspend fun login()
    suspend fun logout()
    suspend fun removeLoginData(accountId: String)
}