package com.hiebeler.loopy.di

import com.hiebeler.loopy.domain.repository.AuthRepository
import com.hiebeler.loopy.domain.usecases.GetCurrentLoginDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AuthModule {

    @Provides
    @Singleton
    fun provideGetCurrentLoginDataUseCase(
        repository: AuthRepository,
    ): GetCurrentLoginDataUseCase = GetCurrentLoginDataUseCase(repository)
}