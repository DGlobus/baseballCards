package com.dasha.service;

import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface ModelService<T, ParamT> {
    T create(ParamT createParams);

    T update(@NonNull UUID id, ParamT params);

    void delete(@NonNull UUID id);

    T getById(@NonNull UUID id);

    List<T> getAll();
}
