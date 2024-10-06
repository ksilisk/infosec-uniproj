package com.ksilisk.infosec.initialize;

import java.io.IOException;

public interface Initializable {
    void initialize() throws IOException;

    default int getOrder() {
        return Integer.MAX_VALUE;
    }
}
