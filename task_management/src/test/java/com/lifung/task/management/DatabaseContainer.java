package com.lifung.task.management;

import org.testcontainers.containers.MySQLContainer;

public class DatabaseContainer extends MySQLContainer<DatabaseContainer> {
    private static final String IMAGE_VERSION = "mysql:5.7";
    private static DatabaseContainer container;

    private DatabaseContainer() {
        super(IMAGE_VERSION);
    }

    public static DatabaseContainer getInstance() {
        if (container == null) {
            container = new DatabaseContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
