package com.ksilisk.infosec.initialization;

import com.ksilisk.infosec.config.ApplicationProperties;
import com.ksilisk.infosec.context.DefaultApplicationContext;
import com.ksilisk.infosec.database.DatabaseClient;
import com.ksilisk.infosec.repository.DefaultUserRepository;
import com.ksilisk.infosec.security.DefaultCipherCoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum DefaultApplicationInitializer implements ApplicationInitializer {
    INSTANCE;

    private final ApplicationProperties appProperties = ApplicationProperties.INSTANCE;

    private final List<Initializable> initializables = new ArrayList<>();

    DefaultApplicationInitializer() {
        initializables.addAll(List.of(
                ApplicationProperties.INSTANCE,
                DefaultApplicationContext.INSTANCE,
                DefaultUserRepository.INSTANCE,
                DatabaseClient.INSTANCE,
                DefaultCipherCoder.INSTANCE
        ));
    }

    @Override
    public void initialize() throws Exception {
        createDirectoriesIfNeeded();
        initializables.sort(Comparator.comparingInt(Initializable::getOrder));
        for (Initializable initializable : initializables) {
            initializable.initialize();
        }
    }

    private void createDirectoriesIfNeeded() throws IOException {
        Path resourcesPath = Paths.get(appProperties.getApplicationResourcesPath());
        if (!Files.exists(resourcesPath)) {
            Files.createDirectories(resourcesPath);
        }
        Path tmpPath = Paths.get(appProperties.getApplicationTmpDirectoryPath());
        if (!Files.exists(tmpPath)) {
            Files.createDirectories(tmpPath);
        }
    }
}
