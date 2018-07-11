package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeFilter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            if (repository.get(meal.getId()).getUserId() == userId)
                return repository.put(meal.getId(), meal);
            else
                return null;
        }


    }

    @Override
    public boolean delete(int id, Integer userId) {
        Meal meal = repository.get(id);

        if (meal.getUserId() == userId) {
            return repository.remove(id, meal);
        } else
            return false;

    }

    @Override
    public Meal get(int id, Integer userId) {

        Meal meal = repository.getOrDefault(id, new Meal(LocalDateTime.now(), "", 0));
        if (meal.getUserId() == userId)
            return meal;
        else
            return null;
    }

    @Override
    public List<Meal> getAll(Integer userId, DateTimeFilter dateTimeFilter) {

        return repository.values().
                stream().
                filter(meal -> meal.getUserId() == userId).
                filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), dateTimeFilter.getStartDate(), dateTimeFilter.getEndDate())).
                filter(meal -> DateTimeUtil.isBetween(meal.getTime(), dateTimeFilter.getStartTime(), dateTimeFilter.getEndTime())).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.values().
                stream().
                filter(meal -> meal.getUserId() == userId).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());
    }

}

