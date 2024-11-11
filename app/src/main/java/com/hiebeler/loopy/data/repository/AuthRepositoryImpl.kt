package com.hiebeler.loopy.data.repository

import androidx.datastore.core.DataStore
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.remote.dto.AccountDto
import com.hiebeler.loopy.data.remote.dto.LoginModelDto
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.AuthData
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.model.LoginModel
import com.hiebeler.loopy.domain.repository.AuthRepository
import com.hiebeler.loopy.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AuthData>, private val loopsApi: LoopsApi
) : AuthRepository {

    override suspend fun addNewLoginData(newLoginData: LoginData) {
        try {
            dataStore.updateData { authData ->
                authData.copy(
                    loginDataList = authData.loginDataList + newLoginData
                )
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    override suspend fun finishLogin(
        newLoginData: LoginData, currentlyLoggedIn: String
    ) {
        dataStore.updateData { authData ->
            var updatedLoginDataList = authData.loginDataList
            updatedLoginDataList =
                updatedLoginDataList.filter { it.accountId != newLoginData.accountId && it.accountId.isNotBlank() }


            updatedLoginDataList = updatedLoginDataList + newLoginData

            authData.copy(
                loginDataList = updatedLoginDataList, currentlyLoggedIn = currentlyLoggedIn
            )
        }
    }

    override suspend fun updateCurrentUser(accountId: String) {
        dataStore.updateData { authData ->
            if (authData.loginDataList.any { it.accountId == accountId }) {
                authData.copy(currentlyLoggedIn = accountId)
            } else {
                authData
            }
        }
    }

    override suspend fun getAuthData(): AuthData {
        return dataStore.data.first()
    }

    override suspend fun login(email: String, password: String): Flow<Resource<LoginModel>> {
        return NetworkCall<LoginModel, LoginModelDto>().makeCall(
            loopsApi.login(
                email, password
            )
        )
    }

    override suspend fun logout() {
        dataStore.updateData { authData ->
            authData.copy(
                loginDataList = authData.loginDataList.filter { loginData -> loginData.accountId != authData.currentlyLoggedIn },
                currentlyLoggedIn = ""
            )
        }
    }

    override suspend fun removeLoginData(accountId: String) {
        dataStore.updateData { authData ->
            authData.copy(loginDataList = authData.loginDataList.filter { loginData -> loginData.accountId != accountId })
        }
    }
}