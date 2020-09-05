package edu.mirmil.domain.component

import org.springframework.beans.factory.annotation.Value
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.persistence.AttributeConverter
import javax.persistence.Convert

@Convert
class CryptConverter : AttributeConverter<String, String> {

    private val algorithm = "AES"

    @Value("\${crypt.key}")
    lateinit var key: String

    // Encrypt
    override fun convertToDatabaseColumn(attribute: String): String {
        val cipher = cipherAndSpec(Cipher.ENCRYPT_MODE)
        val crypt = cipher.doFinal(attribute.toByteArray())
        return Base64.getEncoder().encodeToString(crypt)
    }

    // Decrypt
    override fun convertToEntityAttribute(dbData: String): String {
        val cipher = cipherAndSpec(Cipher.DECRYPT_MODE)
        val decode = Base64.getDecoder().decode(dbData)
        val decrypt = cipher.doFinal(decode)
        return String(decrypt)
    }

    private fun cipherAndSpec(mode: Int): Cipher {
        val cipher = Cipher.getInstance(algorithm)
        val spec = SecretKeySpec(key.toByteArray(), algorithm)
        cipher.init(mode, spec)
        return cipher
    }
}