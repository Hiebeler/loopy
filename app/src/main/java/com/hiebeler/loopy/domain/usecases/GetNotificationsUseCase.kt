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
    operator fun invoke(nextCursor: String = ""): Flow<Resource<NotificationsWrapper>> {
        return userRepository.getNotifications(nextCursor)
    }
}