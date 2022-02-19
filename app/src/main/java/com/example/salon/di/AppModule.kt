package com.example.salon.di

import android.content.Context
import com.example.salon.repository.SalonRepository
import com.example.salon.retrofit.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiClient(@ApplicationContext context: Context) = ApiClient(context)

    @Singleton
    @Provides
    fun provideSalonRepository(apiClient: ApiClient) = SalonRepository(apiClient)
}