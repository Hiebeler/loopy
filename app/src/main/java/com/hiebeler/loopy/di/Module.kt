package com.hiebeler.loopy.di

import LoopsApi
import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    @Singleton
    fun provideUserDataStorePreferences(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.dataStore
    }


    @Provides
    @Singleton
    fun provideHostSelectionInterceptor(): HostSelectionInterceptorInterface =
        HostSelectionInterceptor()


    @Provides
    @Singleton
    fun provideOKHttpClient(hostSelectionInterceptor: HostSelectionInterceptorInterface): OkHttpClient {

        var loggi = HttpLoggingInterceptor()
        loggi.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().addInterceptor(hostSelectionInterceptor).addInterceptor(loggi)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder().addConverterFactory(
        GsonConverterFactory.create()
    ).client(client).baseUrl("https://err.or/").build()


    @Provides
    @Singleton
    fun providePixelfedApi(retrofit: Retrofit): LoopsApi =
        retrofit.create(LoopsApi::class.java)
}