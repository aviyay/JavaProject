package com.bnet.shared.model.backend;

import java.util.List;

public interface ProvidableRepository<T extends Providable> {
    long addAndReturnAssignedId(T item);
    List<T> getAll();
    List<T> getAllNews();
    T getOrNull(long id);
    boolean isSomethingNew();
    void reset();
}
