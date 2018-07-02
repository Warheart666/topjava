package ru.javawebinar.topjava.crud;

import java.util.List;

public interface Crud<T, Id extends Integer> {

    T create(T t);

    T read(Id id);

    T update(T t);

    void delete(T t);

    List<T> getAll();
}
