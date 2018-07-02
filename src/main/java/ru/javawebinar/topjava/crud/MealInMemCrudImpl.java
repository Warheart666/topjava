package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealInMemCrudImpl implements Crud<Meal, Integer> {


    private static int seq = 1;
    private static Map<Integer, Meal> storage = new HashMap();

    @Override
    public synchronized Meal create(Meal meal) {

        if (meal != null && meal.getId() == 0) {
            meal.setId(seq);
            storage.putIfAbsent(seq, meal);
            seq++;
        }

        return meal;
    }

    @Override
    public Meal read(Integer integer) {
        return storage.get(integer);
    }

    @Override
    public synchronized Meal update(Meal meal) {
        return storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(Meal meal) {

    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
