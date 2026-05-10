package com.studysync.factory;

import com.studysync.repository.*;
import com.studysync.repository.inmemory.*;
import com.studysync.repository.stubs.*;

/**
 * Factory that returns the correct repository implementation
 * based on the requested storage type.
 *
 * Usage:
 *   UserRepository repo = RepositoryFactory.getUserRepository("MEMORY");
 *
 * Supported storage types:
 *   "MEMORY"     — fast in-memory HashMap (default for tests and development)
 *   "DATABASE"   — stub for a future SQL/JPA implementation
 *   "FILESYSTEM" — stub for a future JSON file-based implementation
 *
 * To add a new storage backend, implement the repository interface
 * and add a new case to the relevant method here — no other code changes required.
 */
public class RepositoryFactory {

    private RepositoryFactory() {
        // Utility class — not instantiable
    }

    public static UserRepository getUserRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":     return new InMemoryUserRepository();
            case "DATABASE":   return new DatabaseUserRepository();
            case "FILESYSTEM": return new FileSystemUserRepository();
            default: throw new IllegalArgumentException(
                "Unknown storage type: '" + storageType + "'. Valid options: MEMORY, DATABASE, FILESYSTEM");
        }
    }

    public static StudyGroupRepository getStudyGroupRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":     return new InMemoryStudyGroupRepository();
            case "DATABASE":   return new DatabaseStudyGroupRepository();
            case "FILESYSTEM": return new FileSystemStudyGroupRepository();
            default: throw new IllegalArgumentException(
                "Unknown storage type: '" + storageType + "'");
        }
    }

    public static MembershipRepository getMembershipRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":   return new InMemoryMembershipRepository();
            case "DATABASE": return new DatabaseMembershipRepository();
            default: throw new IllegalArgumentException(
                "Unknown storage type: '" + storageType + "'");
        }
    }

    public static StudySessionRepository getStudySessionRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":   return new InMemoryStudySessionRepository();
            case "DATABASE": return new DatabaseStudySessionRepository();
            default: throw new IllegalArgumentException(
                "Unknown storage type: '" + storageType + "'");
        }
    }

    public static CourseRepository getCourseRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":   return new InMemoryCourseRepository();
            case "DATABASE": return new DatabaseCourseRepository();
            default: throw new IllegalArgumentException(
                "Unknown storage type: '" + storageType + "'");
        }
    }
}
