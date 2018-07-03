package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MealWithExceedInMemCrudImpl implements Crud<MealWithExceed> {


    private static ConcurrentMap<Integer, MealWithExceed> storage = new ConcurrentHashMap<>();

    @Override
    public MealWithExceed create(MealWithExceed mealWithExceed) {

        storage.putIfAbsent(mealWithExceed.getId(), mealWithExceed);
        return mealWithExceed;
    }

    @Override
    public MealWithExceed read(Integer integer) {
        return null;
    }

    @Override
    public MealWithExceed update(MealWithExceed mealWithExceed) {
        return null;
    }

    @Override
    public void delete(MealWithExceed mealWithExceed) {

    }

    @Override
    public List<MealWithExceed> getAll() {
        return new ArrayList<>(storage.values());
    }
}
