package com.yabraa.keystoreapp.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yabraa.keystoreapp.databinding.ActivityMainBinding
import com.yabraa.keystoreapp.key_store_manager.CryptDataHandler
import dagger.hilt.android.AndroidEntryPoint
import java.util.Base64
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var cryptDataHandler: CryptDataHandler

    private var cipherText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnEncryptClicked()
        setOnDecryptClicked()
    }


    private fun setOnEncryptClicked() = binding.encryptBtn.setOnClickListener {
        if (binding.editText.text.isNullOrEmpty()) {
            binding.editText.error = "Please enter text"
            return@setOnClickListener
        }
        val byteArray = binding.editText.text.toString().toByteArray()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cipherText = Base64.getEncoder().encodeToString(cryptDataHandler.encrypt(byteArray))
        }
        binding.editText.setText(cipherText)
    }

    private fun setOnDecryptClicked() = binding.decryptBtn.setOnClickListener {
        val originalText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cryptDataHandler.decrypt(Base64.getDecoder().decode(cipherText))?.decodeToString()
        } else {
            return@setOnClickListener
        }
        binding.editText.setText(originalText)
    }
}