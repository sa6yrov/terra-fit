package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;

import java.util.List;

public interface BaseService<T> {
    T save (T t);

    T getById(Long id) throws SubscriptionNotFoundException;

    List<T> getAll();
}
