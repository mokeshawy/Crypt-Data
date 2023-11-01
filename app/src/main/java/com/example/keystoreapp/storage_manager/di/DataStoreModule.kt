package com.example.keystoreapp.storage_manager.di

import android.content.Context
import com.example.keystoreapp.storage_manager.DataStoreHandler
import com.example.keystoreapp.storage_manager.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreHandler =
        DataStoreManager(context)
}