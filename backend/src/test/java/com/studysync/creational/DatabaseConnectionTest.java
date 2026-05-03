package com.studysync.creational;

import com.studysync.creational.singleton.DatabaseConnection;
import com.studysync.creational.singleton.DatabaseConnectionEager;
import com.studysync.creational.singleton.DatabaseConnectionSync;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    // ── Holder-based Singleton (main implementation) ──────────────────────────

    @Test
    void testSingletonReturnsSameInstance() {
        System.out.println("\n=== TEST: Singleton Returns Same Instance ===");
        System.out.println("Getting two references via getInstance()...");

        DatabaseConnection conn1 = DatabaseConnection.getInstance();
        DatabaseConnection conn2 = DatabaseConnection.getInstance();

        System.out.println("Instance 1 hash: " + System.identityHashCode(conn1));
        System.out.println("Instance 2 hash: " + System.identityHashCode(conn2));

        assertNotNull(conn1, "getInstance() must not return null");
        assertSame(conn1, conn2, "Both calls must return the exact same instance");

        System.out.println("✓ PASS — both references point to the same singleton instance");
    }

    @Test
    void testSingletonThreadSafety() throws InterruptedException {
        System.out.println("\n=== TEST: Singleton Thread Safety ===");
        System.out.println("Spawning 10 concurrent threads to retrieve the singleton...");

        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        DatabaseConnection[] connections = new DatabaseConnection[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                connections[index] = DatabaseConnection.getInstance();
                System.out.println("Thread " + index + " → instance hash: "
                        + System.identityHashCode(connections[index]));
            });
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS),
                "All threads should finish within 5 seconds");

        DatabaseConnection expected = DatabaseConnection.getInstance();
        for (int i = 0; i < threadCount; i++) {
            assertNotNull(connections[i], "Thread " + i + " must receive a non-null instance");
            assertSame(expected, connections[i],
                    "Thread " + i + " must receive the same instance as the canonical singleton");
        }

        System.out.println("✓ PASS — all 10 threads received the identical singleton instance");
    }

    @Test
    void testQueryCountIncrementsCorrectly() {
        System.out.println("\n=== TEST: Query Count Increments Correctly ===");

        DatabaseConnection conn = DatabaseConnection.getInstance();
        int before = conn.getQueryCount();
        System.out.println("Query count before: " + before);

        conn.executeQuery("SELECT * FROM users");
        conn.executeQuery("SELECT * FROM study_groups");

        int after = conn.getQueryCount();
        System.out.println("Query count after:  " + after);

        assertEquals(before + 2, after,
                "Query count must increase by exactly 2 after two executeQuery calls");

        System.out.println("✓ PASS — query counter incremented by 2 as expected");
    }

    @Test
    void testConnectionDetailsAreValid() {
        System.out.println("\n=== TEST: Connection Details Are Valid ===");

        DatabaseConnection conn = DatabaseConnection.getInstance();

        String url = conn.getConnectionUrl();
        System.out.println("Connection URL : " + url);
        assertNotNull(url, "Connection URL must not be null");
        assertTrue(url.startsWith("jdbc:"),
                "URL must be a valid JDBC connection string starting with 'jdbc:'");

        System.out.println("Connected at   : " + conn.getConnectedAt());
        assertNotNull(conn.getConnectedAt(), "connectedAt timestamp must not be null");

        System.out.println("Is connected   : " + conn.isConnected());
        assertTrue(conn.isConnected(), "Connection must report as active");

        System.out.println("✓ PASS — connection URL, timestamp, and status are all valid");
    }

    // ── Eager Singleton variant ───────────────────────────────────────────────

    @Test
    void testEagerSingletonReturnsSameInstance() {
        System.out.println("\n=== TEST: Eager Singleton Returns Same Instance ===");

        DatabaseConnectionEager e1 = DatabaseConnectionEager.getInstance();
        DatabaseConnectionEager e2 = DatabaseConnectionEager.getInstance();

        System.out.println("Eager instance 1 hash: " + System.identityHashCode(e1));
        System.out.println("Eager instance 2 hash: " + System.identityHashCode(e2));

        assertNotNull(e1, "Eager singleton must not return null");
        assertSame(e1, e2, "Eager singleton must always return the same instance");

        System.out.println("✓ PASS — eager singleton instance is the same object");
    }

    @Test
    void testEagerSingletonHasValidUrl() {
        System.out.println("\n=== TEST: Eager Singleton Has Valid Connection URL ===");

        DatabaseConnectionEager conn = DatabaseConnectionEager.getInstance();
        System.out.println("URL: " + conn.getConnectionUrl());

        assertNotNull(conn.getConnectionUrl(), "Eager singleton URL must not be null");
        assertTrue(conn.getConnectionUrl().startsWith("jdbc:"),
                "Eager singleton URL must be a valid JDBC connection string");

        System.out.println("✓ PASS — eager singleton has a valid JDBC URL");
    }

    // ── Synchronized (double-checked locking) Singleton variant ──────────────

    @Test
    void testSyncSingletonReturnsSameInstance() {
        System.out.println("\n=== TEST: Sync Singleton Returns Same Instance ===");

        DatabaseConnectionSync s1 = DatabaseConnectionSync.getInstance();
        DatabaseConnectionSync s2 = DatabaseConnectionSync.getInstance();

        System.out.println("Sync instance 1 hash: " + System.identityHashCode(s1));
        System.out.println("Sync instance 2 hash: " + System.identityHashCode(s2));

        assertNotNull(s1, "Sync singleton must not return null");
        assertSame(s1, s2, "Double-checked locking singleton must return the same instance");

        System.out.println("✓ PASS — synchronized singleton instance is the same object");
    }

    @Test
    void testSyncSingletonQueryCount() {
        System.out.println("\n=== TEST: Sync Singleton Query Count ===");

        DatabaseConnectionSync conn = DatabaseConnectionSync.getInstance();
        int before = conn.getQueryCount();
        System.out.println("Before: " + before);

        conn.executeQuery("SELECT 1");

        int after = conn.getQueryCount();
        System.out.println("After:  " + after);

        assertEquals(before + 1, after,
                "Sync singleton query count must increment by 1 after one executeQuery call");

        System.out.println("✓ PASS — sync singleton query count incremented correctly");
    }
}
