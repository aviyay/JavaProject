package com.bnet.data.model.backend;

import java.util.List;

public interface ProvidableRepository<T extends Providable> {
    int addAndReturnAssignedId(T item);
    List<T> getAll();
    List<T> getAllNews();
    void clear();
}
