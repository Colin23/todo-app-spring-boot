package com.colinmoerbe.javatodoapp.todo;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodoConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoConfig.class);

    @PostConstruct
    void initialized() {
        // TODO: Make sure this works. Perhaps another version has to be used, as the version is written in the version.properties file
        LOGGER.info("(translation-backend) v{} initialized.", getClass().getPackage().getImplementationVersion());
    }
}
