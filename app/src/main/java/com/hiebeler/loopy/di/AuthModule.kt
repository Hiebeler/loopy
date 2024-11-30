package com.hiebeler.loopy.di

import com.daniebeler.pfpixelix.di.HostSelectionInterceptorInterface
import com.daniebeler.pfpixelix.domain.usecase.RemoveLoginDataUseCase
import com.daniebeler.pfpixelix.domain.usecase.UpdateCurrentUserUseCase
import com.hiebeler.loopy.domain.repository.AuthRepository
import com.hiebeler.loopy.domain.usecases.GetAuthDataUseCase
import com.hiebeler.loopy.domain.usecases.GetCurrentLoginDataUseCase
import com.hiebeler.loopy.domain.usecases.LogoutUseCase
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

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        repository: AuthRepository,
    ): LogoutUseCase = LogoutUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveLoginDataUseCase(
        repository: AuthRepository,
    ): RemoveLoginDataUseCase =
        RemoveLoginDataUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAuthDataUseCase(
        repository: AuthRepository,
    ): GetAuthDataUseCase =
        GetAuthDataUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateCurrentUserUseCase(
        repository: AuthRepository,
        hostSelectionInterceptorInterface: HostSelectionInterceptorInterface
    ): UpdateCurrentUserUseCase =
        UpdateCurrentUserUseCase(repository, hostSelectionInterceptorInterface)
}