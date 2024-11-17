package com.hiebeler.loopy.di

import com.hiebeler.loopy.domain.repository.SearchRepository
import com.hiebeler.loopy.domain.usecases.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SearchModule {

    @Provides
    @Singleton
    fun provideSearchUseCase(
        repository: SearchRepository
    ): SearchUseCase = SearchUseCase(repository)

}