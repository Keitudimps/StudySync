package com.studysync.creational;

import com.studysync.creational.singleton.DatabaseConnection;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    
    @Test
    void testSingletonReturnsSameInstance() {
        DatabaseConnection conn1 = DatabaseConnection.getInstance();
        DatabaseConnection conn2 = DatabaseConnection.getInstance();
        
        assertSame(conn1, conn2);
    }
    
    @Test
    void testSingletonThreadSafety() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        DatabaseConnection[] connections = new DatabaseConnection[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                connections[index] = DatabaseConnection.getInstance();
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        DatabaseConnection first = connections[0];
        for (int i = 1; i < threadCount; i++) {
            assertSame(first, connections[i]);
        }
    }
    
    @Test
    void testQueryExecution() {
        DatabaseConnection conn = DatabaseConnection.getInstance();
        int initialCount = conn.getQueryCount();
        
        conn.executeQuery("SELECT * FROM users");
        conn.executeQuery("SELECT * FROM groups");
        
        assertEquals(initialCount + 2, conn.getQueryCount());
    }
    
    @Test
    void testConnectionDetails() {
        DatabaseConnection conn = DatabaseConnection.getInstance();
        assertNotNull(conn.getConnectionUrl());
        assertNotNull(conn.getConnectedAt());
        assertTrue(conn.isConnected());
    }
}
