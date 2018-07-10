package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeFilter;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, Integer userId);

    Meal delete(int id, Integer userId);

    Meal get(int id, Integer userId);

    Collection<Meal> getAll(Integer userId, DateTimeFilter dateTimeFilter);
}
