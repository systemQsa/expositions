package com.myproject.expo.expositions.service.encrypt;

import com.myproject.expo.expositions.exception.ValidationException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PassEncrypt {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;

    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Accepts clear user password and special key for encryption process
     *
     * @param pText - gets clear password
     * @param key   - specific key for encryption
     * @return String representation of encrypted password
     * @throws ValidationException in case encryption fail
     */

    public String encrypt(byte[] pText, String key) throws ValidationException {
        byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
        byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
        try {
            SecretKey aesKeyFromPassword = getAESKeyFromPassword(key.toCharArray(), salt);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] cipherText = cipher.doFinal(pText);
            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();
            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
        } catch (Exception e) {
            throw new ValidationException("err.encrypt");
        }
    }

    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

    }

    /**
     * Checks if entered user password is match with stored password in DataBase
     *
     * @param text     - gets plain encrypted text from DataBase
     * @param key      - gets special key for decryption
     * @param password - gets clear user password from browser
     * @return in case if passwords are match returns true else returns false
     * @throws ValidationException in case decryption fail
     */
    public boolean decryptPass(String text, String key, char[] password) throws ValidationException {
        try {
            return decrypt(text, key).equals(String.valueOf(password));
        } catch (Exception e) {
            throw new ValidationException("err.incorrect_password");
        }

    }

    private static String decrypt(String cText, String password) throws Exception {
        ByteBuffer bb = ByteBuffer.wrap(Base64.getDecoder().decode(cText.getBytes(StandardCharsets.UTF_8)));
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);
        byte[] salt = new byte[SALT_LENGTH_BYTE];
        bb.get(salt);
        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);
        SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }
}
