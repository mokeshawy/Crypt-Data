package com.example.keystoreapp.core.key_store_manager

interface CryptDataHandler {
    fun encrypt(bytes: ByteArray): ByteArray
    fun decrypt(bytes: ByteArray): ByteArray?
}