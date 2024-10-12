package com.ksilisk.infosec.database;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.initialization.Initializable;
import com.ksilisk.infosec.security.CipherCoder;
import com.ksilisk.infosec.security.DefaultCipherCoder;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

public enum DatabaseClient implements Initializable, Closeable {
    INSTANCE;

    private final ApplicationProperties properties = ApplicationProperties.INSTANCE;
    private final CipherCoder cipherCoder = DefaultCipherCoder.INSTANCE;

    private Path databasePath;
    private Path tmpFilePath;

    public void save(byte[] bytes) throws Exception {
        Files.write(databasePath, cipherCoder.encrypt(bytes));
    }

    public void writeAheadLog(byte[] bytes) throws IOException {
        byte[] bytesWithNewLine = Arrays.copyOf(bytes, bytes.length + 1);
        bytesWithNewLine[bytes.length] = '\n';
        Files.write(tmpFilePath, bytesWithNewLine, StandardOpenOption.APPEND);
    }

    public byte[] load() throws Exception {
        byte[] data = Files.readAllBytes(databasePath);
        if (data.length == 0) {
            return data;
        }
        return cipherCoder.decrypt(data);
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
        try (Stream<Path> tmpPaths = Files.walk(Paths.get(properties.getApplicationTmpDirectoryPath()))) {
            tmpPaths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
