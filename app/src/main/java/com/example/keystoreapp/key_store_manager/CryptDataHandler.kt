package com.example.keystoreapp.key_store_manager

interface CryptDataHandler {
    fun encrypt(bytes: ByteArray): ByteArray
    fun decrypt(bytes: ByteArray): ByteArray?
}