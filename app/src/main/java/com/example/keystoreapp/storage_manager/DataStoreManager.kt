package com.example.keystoreapp.storage_manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val DATA_STORE_NAME = "DATA_STORE_MANAGER"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreManager @Inject constructor(@ApplicationContext context: Context) :
    DataStoreHandler {
    private val dataStore = context.dataStore

    override suspend fun setBoolean(preferencesKey: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { it[preferencesKey] = value }
    }

    override suspend fun getBoolean(preferencesKey: Preferences.Key<Boolean>) =
        dataStore.data.map { it[preferencesKey] }.first()


    override suspend fun setInteger(preferencesKey: Preferences.Key<Int>, value: Int) {
        dataStore.edit { it[preferencesKey] = value }
    }
    override suspend fun getInteger(preferencesKey: Preferences.Key<Int>) =
        dataStore.data.map { it[preferencesKey] }.first()


    override suspend fun setFloat(preferencesKey: Preferences.Key<Float>, value: Float) {
        dataStore.edit { it[preferencesKey] = value }
    }
    override suspend fun getFloat(preferencesKey: Preferences.Key<Float>) =
        dataStore.data.map { it[preferencesKey] }.first()

    override suspend fun setString(preferencesKey: Preferences.Key<String>, value: String) {
        dataStore.edit { it[preferencesKey] = value }
    }
    override suspend fun getString(preferencesKey: Preferences.Key<String>) =
        dataStore.data.map { it[preferencesKey] }.first()

}