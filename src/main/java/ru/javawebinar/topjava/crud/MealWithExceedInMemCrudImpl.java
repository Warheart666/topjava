package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealWithExceedInMemCrudImpl implements Crud<MealWithExceed, Integer> {


    private static Map<Integer, MealWithExceed> storage = new HashMap();

    @Override
    public synchronized MealWithExceed create(MealWithExceed mealWithExceed) {

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
