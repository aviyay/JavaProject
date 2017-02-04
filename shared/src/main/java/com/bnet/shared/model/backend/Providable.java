package com.bnet.shared.model.backend;

/**
 * An Entity which can be provided by an ID
 */
public interface Providable {
    /**
     * Get ID
     * @return The ID
     */
    long getId();

    /**
     * Set Id
     * @param id The ID to be set
     */
    void setId(long id);
}
