package com.hiebeler.loopy.data.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.remote.dto.AccountDto
import com.hiebeler.loopy.data.remote.dto.AccountWrapperDto
import com.hiebeler.loopy.data.remote.dto.FeedWrapperDto
import com.hiebeler.loopy.data.remote.dto.FollowersWrapperDto
import com.hiebeler.loopy.data.remote.dto.MetaAccountDto
import com.hiebeler.loopy.data.remote.dto.NotificationsWrapperDto
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.model.FollowersWrapper
import com.hiebeler.loopy.domain.model.MetaAccount
import com.hiebeler.loopy.domain.model.NotificationsWrapper
import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val loopsApi: LoopsApi
) : UserRepository {

    override fun getOwnUser(): Flow<Resource<Account>> {
        return NetworkCall<Account, AccountDto>().makeCall(
            loopsApi.getOwnUser()
        )
    }

    override fun getUser(accountId: String): Flow<Resource<Account>> {
        return NetworkCall<Account, AccountWrapperDto>().makeCall(
            loopsApi.getUser(
                accountId
            )
        )
    }

    override fun getPostsOfUser(accountId: String): Flow<Resource<FeedWrapper>> {
        return NetworkCall<FeedWrapper, FeedWrapperDto>().makeCall(
            loopsApi.getPostsOfUser(
                accountId
            )
        )
    }

    override fun getPostsOfOwnUser(accountId: String): Flow<Resource<FeedWrapper>> {
        return NetworkCall<FeedWrapper, FeedWrapperDto>().makeCall(
            loopsApi.getPostsOfOwnUser()
        )
    }

    override fun getFollowers(accountId: String): Flow<Resource<FollowersWrapper>> {
        return NetworkCall<FollowersWrapper, FollowersWrapperDto>().makeCall(
            loopsApi.getFollowers(
                accountId
            )
        )
    }

    override fun getFollowing(accountId: String): Flow<Resource<FollowersWrapper>> {
        return NetworkCall<FollowersWrapper, FollowersWrapperDto>().makeCall(
            loopsApi.getFollowing(
                accountId
            )
        )
    }

    override fun getNotifications(nextCursor: String): Flow<Resource<NotificationsWrapper>> {
        return NetworkCall<NotificationsWrapper, NotificationsWrapperDto>().makeCall(
            loopsApi.getNotifications(nextCursor)
        )
    }

    override fun followUser(accountId: String): Flow<Resource<MetaAccount>> {
        return NetworkCall<MetaAccount,MetaAccountDto>().makeCall(
            loopsApi.followUser(accountId)
        )
    }

    override fun unfollowUser(accountId: String): Flow<Resource<MetaAccount>> {
        return NetworkCall<MetaAccount,MetaAccountDto>().makeCall(
            loopsApi.unfollowUser(accountId)
        )
    }
}