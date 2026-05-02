package com.studysync.creational.singleton;

/**
 * Eager Initialization Singleton variant.
 * Instance is created at class loading time — simpler but wastes
 * resources if getInstance() is never called.
 */
public class DatabaseConnectionEager {
    private static final DatabaseConnectionEager INSTANCE = new DatabaseConnectionEager();
    private final String connectionUrl;
    private int queryCount = 0;

    private DatabaseConnectionEager() {
        String url = System.getenv("DATABASE_URL");
        this.connectionUrl = (url != null) ? url : "jdbc:postgresql://localhost:5432/studysync";
        System.out.println("[EagerSingleton] Instance created at class load time.");
    }

    public static DatabaseConnectionEager getInstance() {
        return INSTANCE;
    }

    public void executeQuery(String query) {
        queryCount++;
        System.out.println("[EagerSingleton] Executing query #" + queryCount + ": " + query);
    }

    public String getConnectionUrl() { return connectionUrl; }
    public int getQueryCount()       { return queryCount; }
}
