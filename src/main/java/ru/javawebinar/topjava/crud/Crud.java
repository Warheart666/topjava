package ru.javawebinar.topjava.crud;

import java.util.List;

public interface Crud<T> {

    T create(T t);

    T read(Integer id);

    T update(T t);

    void delete(T t);

    List<T> getAll();
}
