package com.studysync.creational;

import com.studysync.creational.singleton.*;
import org.junit.jupiter.api.*;
import java.util.concurrent.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Each test class runs in its own forked JVM (configured in pom.xml via reuseForks=false),
 * so the Singleton state is completely fresh for every test CLASS.
 * Within the class, tests share the same singleton — assertions use relative
 * (before + N) counts, never absolute values, to remain order-independent.
 */
@DisplayName("Singleton Pattern — DatabaseConnection (3 variants)")
class DatabaseConnectionTest {

    // ── Holder-based Singleton ────────────────────────────────────────────────

    @Test
    @DisplayName("getInstance() always returns the exact same object reference")
    void testSingletonReturnsSameInstance() {
        System.out.println("\n--- TEST: Holder Singleton — Same Instance ---");

        DatabaseConnection a = DatabaseConnection.getInstance();
        DatabaseConnection b = DatabaseConnection.getInstance();
        DatabaseConnection c = DatabaseConnection.getInstance();

        System.out.println("  Call 1 hash : " + System.identityHashCode(a));
        System.out.println("  Call 2 hash : " + System.identityHashCode(b));
        System.out.println("  Call 3 hash : " + System.identityHashCode(c));

        assertSame(a, b, "First and second calls must return identical reference");
        assertSame(b, c, "Second and third calls must return identical reference");

        System.out.println("  All three calls returned the same object: confirmed");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("queryCount increases by exactly 2 after two executeQuery() calls")
    void testQueryCountIncrementsCorrectly() {
        System.out.println("\n--- TEST: Holder Singleton — Query Count ---");

        DatabaseConnection conn = DatabaseConnection.getInstance();
        int before = conn.getQueryCount();
        System.out.println("  Query count before calls : " + before);

        conn.executeQuery("SELECT * FROM users");
        conn.executeQuery("SELECT * FROM study_groups");

        int after = conn.getQueryCount();
        System.out.println("  Query count after 2 calls: " + after);

        assertEquals(before + 2, after,
            "Query count must increase by exactly 2 — if this fails, " +
            "executeQuery() is not incrementing queryCount correctly");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Connection URL is a valid JDBC string and isConnected() is true")
    void testConnectionDetailsAreValid() {
        System.out.println("\n--- TEST: Holder Singleton — Connection Details ---");

        DatabaseConnection conn = DatabaseConnection.getInstance();

        String  url       = conn.getConnectionUrl();
        boolean connected = conn.isConnected();

        System.out.println("  URL         : " + url);
        System.out.println("  Connected   : " + connected);
        System.out.println("  Created at  : " + conn.getConnectedAt());

        assertNotNull(url, "getConnectionUrl() must not return null");
        assertTrue(url.startsWith("jdbc:"),
            "URL must start with 'jdbc:' — if this fails, the default URL was changed");
        assertTrue(connected,
            "isConnected() must return true after construction — " +
            "if this fails, the constructor is setting isConnected=false");
        assertNotNull(conn.getConnectedAt(),
            "getConnectedAt() must not return null");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("10 concurrent threads all receive the same singleton instance")
    void testSingletonThreadSafety() throws InterruptedException {
        System.out.println("\n--- TEST: Holder Singleton — Thread Safety (10 threads) ---");

        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        Set<Integer> identityHashes = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                DatabaseConnection instance = DatabaseConnection.getInstance();
                int hash = System.identityHashCode(instance);
                identityHashes.add(hash);
                System.out.println("  Thread " + idx + " → hash: " + hash);
            });
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);
        assertTrue(finished, "All threads must complete within 5 seconds");

        System.out.println("  Distinct identity hashes collected: " + identityHashes.size());

        assertEquals(1, identityHashes.size(),
            "All 10 threads must receive the exact same instance — " +
            "if this is > 1, the Holder singleton is not thread-safe and created multiple instances");

        System.out.println("  PASS");
    }

    // ── Eager Singleton variant ───────────────────────────────────────────────

    @Test
    @DisplayName("Eager singleton: multiple calls return the same instance")
    void testEagerSingletonReturnsSameInstance() {
        System.out.println("\n--- TEST: Eager Singleton — Same Instance ---");

        DatabaseConnectionEager x = DatabaseConnectionEager.getInstance();
        DatabaseConnectionEager y = DatabaseConnectionEager.getInstance();

        System.out.println("  Call 1 hash : " + System.identityHashCode(x));
        System.out.println("  Call 2 hash : " + System.identityHashCode(y));

        assertSame(x, y,
            "Eager singleton must return the same instance every time — " +
            "if this fails, the INSTANCE field was removed or made non-static");
        assertTrue(x.getConnectionUrl().startsWith("jdbc:"),
            "Eager singleton URL must be a valid JDBC string");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Eager singleton: queryCount increases by 1 after one executeQuery() call")
    void testEagerSingletonQueryCount() {
        System.out.println("\n--- TEST: Eager Singleton — Query Count ---");

        DatabaseConnectionEager conn = DatabaseConnectionEager.getInstance();
        int before = conn.getQueryCount();
        System.out.println("  Count before : " + before);

        conn.executeQuery("SELECT 1");
        int after = conn.getQueryCount();
        System.out.println("  Count after  : " + after);

        assertEquals(before + 1, after,
            "Eager singleton queryCount must increase by 1 after one executeQuery()");

        System.out.println("  PASS");
    }

    // ── Synchronized (double-checked locking) Singleton variant ──────────────

    @Test
    @DisplayName("Sync singleton: multiple calls return the same instance")
    void testSyncSingletonReturnsSameInstance() {
        System.out.println("\n--- TEST: Sync Singleton — Same Instance ---");

        DatabaseConnectionSync p = DatabaseConnectionSync.getInstance();
        DatabaseConnectionSync q = DatabaseConnectionSync.getInstance();

        System.out.println("  Call 1 hash : " + System.identityHashCode(p));
        System.out.println("  Call 2 hash : " + System.identityHashCode(q));

        assertSame(p, q,
            "Sync singleton must return the same instance every time — " +
            "if this fails, the volatile+double-check logic was broken");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Sync singleton: queryCount increases by 1 after one executeQuery() call")
    void testSyncSingletonQueryCount() {
        System.out.println("\n--- TEST: Sync Singleton — Query Count ---");

        DatabaseConnectionSync conn = DatabaseConnectionSync.getInstance();
        int before = conn.getQueryCount();
        System.out.println("  Count before : " + before);

        conn.executeQuery("SELECT 1");
        int after = conn.getQueryCount();
        System.out.println("  Count after  : " + after);

        assertEquals(before + 1, after,
            "Sync singleton queryCount must increase by 1 after one executeQuery()");

        System.out.println("  PASS");
    }
}
