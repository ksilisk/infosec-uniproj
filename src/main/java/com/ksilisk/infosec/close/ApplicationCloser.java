package com.ksilisk.infosec.close;

import java.io.Closeable;
import java.io.IOException;

public interface ApplicationCloser extends Closeable {
    void close() throws IOException;
}
