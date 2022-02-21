package com.example.salon.di

import android.content.Context
import androidx.room.Room
import com.example.salon.R
import com.example.salon.repository.SalonRepository
import com.example.salon.retrofit.ApiClient
import com.example.salon.room.CartDatabase
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

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        CartDatabase::class.java,
        context.getString(R.string.app_name)
    ).build()

    @Singleton
    @Provides
    fun provideCartDao(database: CartDatabase) = database.cartDao()
}