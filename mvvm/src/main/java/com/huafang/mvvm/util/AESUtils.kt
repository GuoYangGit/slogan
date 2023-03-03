package com.huafang.mvvm.util

import android.util.Base64
import com.huafang.mvvm.AES_PASSWORD
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AESUtils {

    /**
     * 加密
     * @param content 需要加密的内容
     * @param password  加密密码
     *
     * @return
     */
    fun encrypt(content: String, password: String): String {
        try {
            val key = generateKey(password)
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val result = cipher.doFinal(content.toByteArray())
            return Base64.encodeToString(result, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    fun decrypt(content: String, password: String): String {
        try {
            val key = generateKey(password)
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, key)
            val result = cipher.doFinal(Base64.decode(content, Base64.DEFAULT))
            return String(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 生成加密秘钥
     * @param password
     * @return
     */
    @Throws(Exception::class)
    private fun generateKey(password: String): SecretKeySpec {
        return SecretKeySpec(password.toByteArray(), "AES")
    }
}

fun String.decryptAES(password: String = AES_PASSWORD): String {
    return AESUtils.decrypt(this.removePrefix("ftp://"), password)
}