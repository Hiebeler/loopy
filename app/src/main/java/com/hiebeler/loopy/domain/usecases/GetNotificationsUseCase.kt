package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FollowersWrapper
import com.hiebeler.loopy.domain.model.NotificationsWrapper
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotificationsUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(nextCursor: String = ""): Flow<Resource<NotificationsWrapper?>> = flow {
        emit(Resource.Loading())
        userRepository.getNotifications(nextCursor).collect { result ->
            if (result is Resource.Success) {
                val res: NotificationsWrapper? = result.data
                emit(Resource.Success(res))
            } else {
                emit(Resource.Error(result.message ?: "Something went wrong"))
            }
        }
    }
}