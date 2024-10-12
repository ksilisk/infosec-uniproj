package com.ksilisk.infosec.security;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.initialization.Initializable;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringJoiner;

import static java.nio.charset.StandardCharsets.UTF_8;

public enum DefaultCipherCoder implements CipherCoder, Initializable {
    INSTANCE;

    private final ApplicationProperties applicationProperties = ApplicationProperties.INSTANCE;

    private String algorithm;
    private Cipher encoder;
    private Cipher decoder;
    private MessageDigest hasher;

    @Override
    public void initialize() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.algorithm = applicationProperties.getApplicationSecurityCipherAlgorithm();
        StringJoiner cipherTransformationJoiner = new StringJoiner("/");
        cipherTransformationJoiner.add(applicationProperties.getApplicationSecurityCipherAlgorithm());
        if (applicationProperties.getApplicationSecurityCipherMode() != null
                && !applicationProperties.getApplicationSecurityCipherMode().isBlank()) {
            cipherTransformationJoiner.add(applicationProperties.getApplicationSecurityCipherMode());
        }

        if (applicationProperties.getApplicationSecurityCipherPadding() != null
                && !applicationProperties.getApplicationSecurityCipherPadding().isBlank()) {
            cipherTransformationJoiner.add(applicationProperties.getApplicationSecurityCipherPadding());
        }

        this.hasher = MessageDigest.getInstance(applicationProperties.getApplicationSecurityHashingAlgorithm());
        this.encoder = Cipher.getInstance(cipherTransformationJoiner.toString());
        this.decoder = Cipher.getInstance(cipherTransformationJoiner.toString());
    }

    @Override
    public byte[] encrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException {
        return this.encoder.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException {
        return this.decoder.doFinal(data);
    }

    @Override
    public void setSecretKey(String secretKey) throws InvalidKeyException, InvalidAlgorithmParameterException {
        String saltedKey;
        if (applicationProperties.getApplicationSecurityHashingSalt() != null) {
            saltedKey = applicationProperties.getApplicationSecurityHashingSalt() + ":" + secretKey;
        } else {
            saltedKey = secretKey;
        }
        byte[] hashedKey = hasher.digest(saltedKey.getBytes(UTF_8));
        SecretKey cipherKey = new SecretKeySpec(hashedKey, 0, 16, this.algorithm);
        encoder.init(Cipher.ENCRYPT_MODE, cipherKey, new IvParameterSpec(new byte[16]));
        decoder.init(Cipher.DECRYPT_MODE, cipherKey, new IvParameterSpec(new byte[16]));
    }
}
