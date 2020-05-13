package kg.sabyrov.terrafit.service;

import java.util.List;

public interface BaseService<T> {
    T save (T t);

    T getById(Long id);

    List<T> getAll();
}
