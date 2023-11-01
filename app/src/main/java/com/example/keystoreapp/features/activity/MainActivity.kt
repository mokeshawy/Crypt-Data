package com.example.keystoreapp.features.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.keystoreapp.databinding.ActivityMainBinding
import com.example.keystoreapp.core.key_store_manager.CryptDataHandler
import com.example.keystoreapp.core.storage_manager.prefernce_datastore.DataStoreHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Base64
import javax.inject.Inject

const val SHARED_PREFERENCE_KEY = "KEY_STORE_APP"
const val KEY_STORE_VALUE = "KEY_STORE_VALUE"

val STRING_KEY: Preferences.Key<String> = stringPreferencesKey("STRING_KEY")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var cryptDataHandler: CryptDataHandler

    @Inject
    lateinit var dataStoreHandler: DataStoreHandler

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnEncryptClicked()
        setOnDecryptClicked()
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
    }


    private fun setOnEncryptClicked() = binding.encryptBtn.setOnClickListener {
        if (binding.editText.text.isNullOrEmpty()) {
            binding.editText.error = "Please enter text"
            return@setOnClickListener
        }
        saveEncryptedValue(false, binding.editText.text.toString())
        binding.editText.text.clear()
    }

    private fun setOnDecryptClicked() = binding.decryptBtn.setOnClickListener {
        lifecycleScope.launch {
            binding.editText.setText(getDecryptedValue(false))
        }
    }

    private fun saveEncryptedValue(isDataStore: Boolean, storeValue: String) {
        val byteArray = storeValue.toByteArray()
        val encryptedValue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(cryptDataHandler.encrypt(byteArray))
        } else {
            return
        }
        lifecycleScope.launch {
            saveInLocalStorage(isDataStore, encryptedValue)
        }
    }

    private suspend fun saveInLocalStorage(isDataStore: Boolean, encryptedValue: String) {
        if (isDataStore) {
            dataStoreHandler.setString(STRING_KEY, encryptedValue)
            return
        }
        sharedPreferences.edit().putString(KEY_STORE_VALUE, encryptedValue).apply()
    }

    private suspend fun getDecryptedValue(isDataStore: Boolean): String? {
        val storeValue = decryptedValueFromLocalStorage(isDataStore)
        val originalText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cryptDataHandler.decrypt(Base64.getDecoder().decode(storeValue))?.decodeToString()
        } else {
            ""
        }
        return originalText
    }

    private suspend fun decryptedValueFromLocalStorage(isDataStore: Boolean): String? {
        return if (isDataStore) {
            dataStoreHandler.getString(STRING_KEY)
        } else {
            sharedPreferences.getString(KEY_STORE_VALUE, "")
        }
    }
}