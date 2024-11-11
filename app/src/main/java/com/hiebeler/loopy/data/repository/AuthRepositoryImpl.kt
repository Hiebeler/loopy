package com.hiebeler.loopy.data.repository

import androidx.datastore.core.DataStore
import com.hiebeler.loopy.domain.model.AuthData
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dataStore: DataStore<AuthData>) :
    AuthRepository {

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

    override suspend fun login() {
        TODO("Not yet implemented")
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