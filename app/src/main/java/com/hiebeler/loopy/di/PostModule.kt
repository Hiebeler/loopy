package com.hiebeler.loopy.di

import com.hiebeler.loopy.domain.repository.PostRepository
import com.hiebeler.loopy.domain.usecases.LikePostUseCase
import com.hiebeler.loopy.domain.usecases.UnlikePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PostModule {
    @Provides
    @Singleton
    fun provideLikePostUseCase(
        repository: PostRepository
    ): LikePostUseCase = LikePostUseCase(repository)

    @Provides
    @Singleton
    fun provideUnlikePostUseCase(
        repository: PostRepository
    ): UnlikePostUseCase = UnlikePostUseCase(repository)
}