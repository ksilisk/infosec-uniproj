package com.ksilisk.infosec.security;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

public interface CipherCoder {
    byte[] encrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    byte[] decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    void setSecretKey(String secretKey) throws InvalidKeyException, InvalidAlgorithmParameterException;
}
