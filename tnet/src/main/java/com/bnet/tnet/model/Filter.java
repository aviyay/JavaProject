package com.bnet.tnet.model;

import java.util.List;

interface Filter<T> {
    List<T> filter(List<T> input);
}
