package com.hiebeler.loopy.di

import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
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
}