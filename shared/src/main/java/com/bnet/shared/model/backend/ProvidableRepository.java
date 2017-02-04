package com.bnet.shared.model.backend;

import java.util.List;

/**
 * Repository of providable entities ({@link Providable})
 * @param <T> The Type the Repository stores
 */
public interface ProvidableRepository<T extends Providable> {
    /**
     * Add the entity to the repository
     * @param item The entity to be added
     * @return The ID assigned to the entity
     */
    long addAndReturnAssignedId(T item);

    /**
     * Get all the entities in the repository
     * @return List of all the entities in the repository
     */
    List<T> getAll();

    /**
     * Get all the new entities in the repository
     * @return List of all the new entities in the repository
     */
    List<T> getAllNews();

    /**
     * Get the entity of this ID
     * @param id The ID of the entity to be returned
     * @return The entity if there is one
     *         NULL if there isn't any entity with that ID
     */
    T getOrNull(long id);

    /**
     * Check is there is any new entities in the repository
     * @return Whether there is any new entities in the repository
     */
    boolean isSomethingNew();

    /**
     * Reset the Repository
     */
    void reset();
}
