package com.myproject.expo.expositions.service.encrypt;

import com.myproject.expo.expositions.exception.ValidationException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class PassEncryptTest {
    private static PassEncrypt passEncrypt;
    private static byte[] pText;

    @BeforeClass
    public static void init() {
        passEncrypt = new PassEncrypt();
        pText = "123".getBytes(StandardCharsets.UTF_8);
    }

    @Test
    public void encrypt() {
        assertDoesNotThrow(() -> passEncrypt.encrypt(pText, "key"));
    }

    @Test(expected = ValidationException.class)
    public void decryptPass() throws ValidationException {
        passEncrypt.decryptPass("encryptText", "key", new char[]{'1', '2', '3'});
    }
}