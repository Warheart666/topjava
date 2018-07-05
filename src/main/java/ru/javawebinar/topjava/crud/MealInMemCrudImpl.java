package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealInMemCrudImpl implements Crud<Meal> {


    private  AtomicInteger seq = new AtomicInteger();
    private  ConcurrentMap<Integer,Meal> storage = new ConcurrentHashMap<>();

    @Override
    public Meal create(Meal meal) {

        if (meal != null && meal.getId() == 0) {
            meal.setId(seq.incrementAndGet());
            storage.putIfAbsent(seq.get(), meal);
            return meal;
        }
        else return null;

    }

    @Override
    public Meal read(Integer integer) {
        return storage.get(integer);
    }

    @Override
    public Meal update(Meal meal) {

        if (storage.containsKey(meal.getId()))
        {
            storage.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public void delete(Meal meal) {
        if (meal != null && meal.getId() != 0) {
            storage.remove(meal.getId());
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
