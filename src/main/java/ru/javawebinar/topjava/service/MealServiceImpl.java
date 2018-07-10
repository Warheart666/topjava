package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeFilter;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {


    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }


    @Override
    public Meal create(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, Integer userId)  {
        Meal meal =  repository.delete(id, userId);
        if (meal== null)
            throw new NotFoundException("No data found!");
    }

    @Override
    public Meal get(int id, Integer userId)  {
        Meal meal = repository.get(id, userId);

        if (meal != null)
            return meal;
        else
            throw new NotFoundException("No data found!");
    }

    @Override
    public void update(Meal meal, Integer userId) {
        Meal meal1 = repository.save(meal, userId);
        if (meal== null)
            throw new NotFoundException("No data found!");
    }

    @Override
    public List<Meal> getAll(Integer userId, DateTimeFilter dateTimeFilter) {
        return (List<Meal>) repository.getAll(userId,dateTimeFilter);
    }
}