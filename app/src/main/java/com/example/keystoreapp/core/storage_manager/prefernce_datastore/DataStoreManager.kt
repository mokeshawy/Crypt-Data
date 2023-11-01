package com.example.keystoreapp.core.storage_manager.prefernce_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
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

    override suspend fun <T> setObject(preferencesKey: Preferences.Key<String>, objects: T) {
        val jsonString = GsonBuilder().create().toJson(objects)
        dataStore.edit { it[preferencesKey] = jsonString }
    }

    override suspend fun <T> getObject(preferencesKey: Preferences.Key<String>, type: Class<T>): T? {
        val value = dataStore.data.map { it[preferencesKey] }.first()
        return GsonBuilder().create().fromJson(value, type)
    }

}