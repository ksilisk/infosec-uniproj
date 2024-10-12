package com.ksilisk.infosec.initialization;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface Initializable {
    void initialize() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException;

    default int getOrder() {
        return Integer.MAX_VALUE;
    }
}
