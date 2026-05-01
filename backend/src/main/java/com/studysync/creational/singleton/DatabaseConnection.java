package com.studysync.creational.singleton;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseConnection {
    private final String connectionUrl;
    private final LocalDateTime connectedAt;
    private int queryCount = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private boolean isConnected = true;
    
    private DatabaseConnection() {
        String url = System.getenv("DATABASE_URL");
        if (url == null) {
            url = "jdbc:postgresql://localhost:5432/studysync";
        }
        connectionUrl = url;
        connectedAt = LocalDateTime.now();
        System.out.println("DatabaseConnection created at: " + connectedAt);
    }
    
    private static class Holder {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }
    
    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }
    
    public void executeQuery(String query) {
        if (!isConnected) {
            throw new IllegalStateException("Database connection is closed");
        }
        lock.lock();
        try {
            queryCount++;
            System.out.println("Executing query #" + queryCount + ": " + query);
        } finally {
            lock.unlock();
        }
    }
    
    public void close() {
        lock.lock();
        try {
            isConnected = false;
            System.out.println("Database connection closed. Total queries: " + queryCount);
        } finally {
            lock.unlock();
        }
    }
    
    public String getConnectionUrl() { return connectionUrl; }
    public LocalDateTime getConnectedAt() { return connectedAt; }
    public int getQueryCount() { return queryCount; }
    public boolean isConnected() { return isConnected; }
    
    static void resetForTesting() {
        try {
            java.lang.reflect.Field field = Holder.class.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset singleton", e);
        }
    }
}
