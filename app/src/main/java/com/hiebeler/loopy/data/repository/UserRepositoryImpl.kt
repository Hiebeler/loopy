package com.hiebeler.loopy.data.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.remote.dto.AccountDto
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val loopsApi: LoopsApi
) : UserRepository {

    override suspend fun getOwnUser(): Flow<Resource<Account>> {
        return NetworkCall<Account, AccountDto>().makeCall(
            loopsApi.getOwnUser()
        )
    }
}