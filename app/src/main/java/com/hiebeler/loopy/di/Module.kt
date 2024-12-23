package com.hiebeler.loopy.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.daniebeler.pfpixelix.di.HostSelectionInterceptorInterface
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.repository.AuthRepositoryImpl
import com.hiebeler.loopy.data.repository.PostRepositoryImpl
import com.hiebeler.loopy.data.repository.SearchRepositoryImpl
import com.hiebeler.loopy.data.repository.TimelineRepositoryImpl
import com.hiebeler.loopy.data.repository.UserRepositoryImpl
import com.hiebeler.loopy.domain.model.AuthData
import com.hiebeler.loopy.domain.repository.AuthRepository
import com.hiebeler.loopy.domain.repository.PostRepository
import com.hiebeler.loopy.domain.repository.SearchRepository
import com.hiebeler.loopy.domain.repository.TimelineRepository
import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.utils.AuthDataSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

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
    fun provideAuthRepository(
        dataStore: DataStore<AuthData>, loopsApi: LoopsApi
    ): AuthRepository = AuthRepositoryImpl(dataStore, loopsApi)

    @Provides
    @Singleton
    fun provideUserRepository(
        loopsApi: LoopsApi
    ): UserRepository = UserRepositoryImpl(loopsApi)

    @Provides
    @Singleton
    fun provideTimelineRepository(
        loopsApi: LoopsApi
    ): TimelineRepository = TimelineRepositoryImpl(loopsApi)

    @Provides
    @Singleton
    fun providePostRepository(
        loopsApi: LoopsApi
    ): PostRepository = PostRepositoryImpl(loopsApi)

    @Provides
    @Singleton
    fun provideSearchRepository(
        loopsApi: LoopsApi
    ): SearchRepository = SearchRepositoryImpl(loopsApi)

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
    fun provideLoopsApi(retrofit: Retrofit): LoopsApi = retrofit.create(LoopsApi::class.java)

    @Provides
    @Singleton
    fun provideAuthDataStore(@ApplicationContext context: Context): DataStore<AuthData> =
        DataStoreFactory.create(serializer = AuthDataSerializer(),
            produceFile = { context.dataStoreFile("auth_data_datastore.json") })
}