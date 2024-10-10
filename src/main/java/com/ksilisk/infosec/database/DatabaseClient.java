package com.ksilisk.infosec.database;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.initialize.Initializable;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public enum DatabaseClient implements Initializable, Closeable {
    INSTANCE;

    private final ApplicationProperties properties = ApplicationProperties.INSTANCE;

    private Path databasePath;
    private Path tmpFilePath;

    public void save(byte[] bytes) throws IOException {
        Files.write(databasePath, bytes);
    }

    public void writeAheadLog(byte[] bytes) throws IOException {
        Files.write(tmpFilePath, bytes, StandardOpenOption.APPEND);
    }

    public byte[] load() throws IOException {
        return Files.readAllBytes(databasePath);
    }

    @Override
    public void initialize() throws IOException {
        this.databasePath = Paths.get(properties.getApplicationResourcesPath(), properties.getApplicationUsersDbFilename());
        this.tmpFilePath = Paths.get(properties.getApplicationTmpDirectoryPath(), "infosec_" + UUID.randomUUID() + ".tmp");

        if (!Files.exists(databasePath)) {
            Files.createFile(databasePath);
        }
        Files.createFile(tmpFilePath);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void close() throws IOException {
        Files.delete(tmpFilePath);
    }
}
