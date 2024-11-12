package com.hiebeler.loopy.di

import com.daniebeler.pfpixelix.di.HostSelectionInterceptorInterface
import com.hiebeler.loopy.domain.repository.AuthRepository
import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.domain.usecases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HostSelectionInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: AuthRepository,
        userRepository: UserRepository,
        hostSelectionInterceptor: HostSelectionInterceptorInterface
    ): LoginUseCase = LoginUseCase(repository, userRepository, hostSelectionInterceptor)
}