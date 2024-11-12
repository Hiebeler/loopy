package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.AuthData
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.model.LoginModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun addNewLoginData(newLoginData: LoginData)
    suspend fun finishLogin(newLoginData: LoginData, currentlyLoggedIn: String)
    suspend fun updateCurrentUser(accountId: String)
    suspend fun getAuthData(): AuthData
    suspend fun login(email: String, password: String): Flow<Resource<LoginModel>>
    suspend fun logout()
    suspend fun removeLoginData(accountId: String)
    suspend fun getCurrentLoginData(): LoginData?
}