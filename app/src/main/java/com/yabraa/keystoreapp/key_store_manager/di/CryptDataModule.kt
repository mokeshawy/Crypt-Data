package com.yabraa.keystoreapp.key_store_manager.di

import com.yabraa.keystoreapp.key_store_manager.CryptDataHandler
import com.yabraa.keystoreapp.key_store_manager.CryptDataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CryptDataModule {

    @Provides
    @Singleton
    fun provideCryptDataManger() : CryptDataHandler = CryptDataManager()
}