package com.ksilisk.infosec.close;

import com.ksilisk.infosec.database.DatabaseClient;
import com.ksilisk.infosec.repository.DefaultUserRepository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public enum DefaultApplicationCloser implements ApplicationCloser {
    INSTANCE;

    private final List<Closeable> closeables = new ArrayList<>();

    DefaultApplicationCloser() {
        closeables.addAll(List.of(
                DefaultUserRepository.INSTANCE,
                DatabaseClient.INSTANCE
        ));
    }

    @Override
    public void close() throws IOException {
        for (Closeable closeable : closeables) {
            closeable.close();
        }
    }
}
