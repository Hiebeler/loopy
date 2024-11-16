package com.hiebeler.loopy.di

import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.domain.usecases.GetFollowersUseCase
import com.hiebeler.loopy.domain.usecases.GetFollowingUseCase
import com.hiebeler.loopy.domain.usecases.GetNotificationsUseCase
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import com.hiebeler.loopy.domain.usecases.GetPostsOfOwnUserUseCase
import com.hiebeler.loopy.domain.usecases.GetPostsOfUserUseCase
import com.hiebeler.loopy.domain.usecases.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserModule {

    @Provides
    @Singleton
    fun provideGetOwnUserUseCase(
        repository: UserRepository
    ): GetOwnUserUseCase = GetOwnUserUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        repository: UserRepository
    ): GetUserUseCase = GetUserUseCase(repository)

    @Provides
    @Singleton
    fun provideGetNotificationsUseCase(
        repository: UserRepository
    ): GetNotificationsUseCase = GetNotificationsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFollowersUseCase(
        repository: UserRepository
    ): GetFollowersUseCase = GetFollowersUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFollowingUseCase(
        repository: UserRepository
    ): GetFollowingUseCase = GetFollowingUseCase(repository)

    @Provides
    @Singleton
    fun provideGetPostsOfUserUseCase(
        repository: UserRepository
    ): GetPostsOfUserUseCase = GetPostsOfUserUseCase(repository)

    @Provides
    @Singleton
    fun provideGetPostsOfOwnUserUseCase(
        repository: UserRepository
    ): GetPostsOfOwnUserUseCase = GetPostsOfOwnUserUseCase(repository)
}