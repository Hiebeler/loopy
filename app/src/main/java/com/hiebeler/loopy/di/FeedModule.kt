package com.hiebeler.loopy.di

import com.hiebeler.loopy.domain.repository.TimelineRepository
import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.domain.usecases.GetForYouFeedUseCase
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FeedModule {

    @Provides
    @Singleton
    fun provideForYouFeedUseCase(
        repository: TimelineRepository
    ): GetForYouFeedUseCase = GetForYouFeedUseCase(repository)
}