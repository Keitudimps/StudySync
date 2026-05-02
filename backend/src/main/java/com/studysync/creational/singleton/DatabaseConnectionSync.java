package com.studysync.creational.singleton;

/**
 * Synchronized (thread-safe) Singleton variant using double-checked locking.
 * Preferred when lazy initialization is needed in a multi-threaded environment
 * but the Initialization-on-Demand Holder idiom is not available.
 */
public class DatabaseConnectionSync {
    private static volatile DatabaseConnectionSync instance;
    private final String connectionUrl;
    private int queryCount = 0;

    private DatabaseConnectionSync() {
        String url = System.getenv("DATABASE_URL");
        this.connectionUrl = (url != null) ? url : "jdbc:postgresql://localhost:5432/studysync";
        System.out.println("[SyncSingleton] Instance created lazily with double-checked locking.");
    }

    public static DatabaseConnectionSync getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionSync.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionSync();
                }
            }
        }
        return instance;
    }

    public void executeQuery(String query) {
        synchronized (this) {
            queryCount++;
            System.out.println("[SyncSingleton] Executing query #" + queryCount + ": " + query);
        }
    }

    public String getConnectionUrl() { return connectionUrl; }
    public int getQueryCount()       { return queryCount; }
}
