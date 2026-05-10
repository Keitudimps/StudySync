package com.studysync.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface defining standard CRUD operations.
 * All entity-specific repositories extend this interface.
 *
 * @param <T>  the domain entity type (e.g., User, StudyGroup)
 * @param <ID> the type of the entity's primary key (e.g., Long)
 */
public interface Repository<T, ID> {

    /**
     * Saves a new entity or updates an existing one.
     * If the entity has no ID, one is generated and assigned.
     * If the entity already has an ID, the stored record is replaced.
     */
    void save(T entity);

    /**
     * Returns the entity with the given ID, or empty if not found.
     */
    Optional<T> findById(ID id);

    /**
     * Returns all stored entities as an unmodifiable list.
     */
    List<T> findAll();

    /**
     * Removes the entity with the given ID.
     * Does nothing if no entity with that ID exists.
     */
    void deleteById(ID id);

    /**
     * Returns true if an entity with the given ID exists in storage.
     */
    boolean existsById(ID id);

    /**
     * Returns the total number of stored entities.
     */
    long count();
}
