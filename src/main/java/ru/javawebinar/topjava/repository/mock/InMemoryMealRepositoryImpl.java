package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeFilter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.util.Collection;
import java.util.Comparator;
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
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            if (isUsersAreEqual(repository.get(meal.getId()).getUserId(), userId))
                return repository.put(meal.getId(), meal);
            else
                return null;
        }


    }

    @Override
    public Meal delete(int id, Integer userId) {
        Meal meal = repository.get(id);

        if (isUsersAreEqual(meal.getUserId(), userId) && meal != null)
            return repository.remove(id);
        else
            return null;

    }

    @Override
    public Meal get(int id, Integer userId) {

        Meal meal = repository.get(id);
        if (isUsersAreEqual(meal.getUserId(), userId) && meal != null)
            return meal;
        else
            return null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId, DateTimeFilter dateTimeFilter) {

        if (dateTimeFilter == null)
            return repository.values().
                    stream().
                    filter(meal -> isUsersAreEqual(meal.getUserId(), userId)).
                    sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                    collect(Collectors.toList());
        else
            return repository.values().
                    stream().
                    filter(meal -> isUsersAreEqual(meal.getUserId(), userId)).
                    filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(),dateTimeFilter.getStartDate(),dateTimeFilter.getEndDate())).
                    filter(meal -> DateTimeUtil.isBetween(meal.getTime(),dateTimeFilter.getStartTime(),dateTimeFilter.getEndTime())).
                    sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                    collect(Collectors.toList());
    }

    private boolean isUsersAreEqual(int repId, int authId) {
        return repId == authId;
    }

}

