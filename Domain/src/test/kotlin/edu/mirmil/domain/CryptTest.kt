package edu.mirmil.domain

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

internal class CryptTest {

    @Test
    @DisplayName("cipher AES")
    fun aesCrypt() {
        // AES 암호화시 key의 바이트 수는 16, 24, 32 여야 한다.
        val key = "optatum_platform"
        val pass = "test"

        val c = Cipher.getInstance("AES")
        val keyByte = key.toByteArray()
        println("keyByte :: ${keyByte.size}")
        val s = SecretKeySpec(keyByte, "AES")
        c.init(Cipher.ENCRYPT_MODE, s)
        val cryptStr = c.doFinal(pass.toByteArray())
        val cryptResult = Base64.getEncoder().encodeToString(cryptStr)
        println("result :: $cryptResult")

        c.init(Cipher.DECRYPT_MODE, s)
        val decryptStr = c.doFinal(cryptStr)
        println(String(decryptStr))

        val cryptBase64Decode = Base64.getDecoder().decode(cryptResult)
        val decryptString = c.doFinal(cryptBase64Decode)
        val decryptResult = String(decryptString)
        println(decryptResult)
    }
}