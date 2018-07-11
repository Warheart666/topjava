package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeFilter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        if (meal != null && meal.getUserId() == userId) {
            repository.remove(id);
            return true;
        } else
            return false;
    }

    @Override
    public Meal get(int id, Integer userId) {

        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId() == userId)
            return meal;
        else
            return null;
    }

    @Override
    public List<Meal> getAll(Integer userId, DateTimeFilter dateTimeFilter) {

        Stream<Meal> mealStream = repository.values().
                stream().
                filter(meal -> meal.getUserId() == userId).
                sorted(Comparator.comparing(Meal::getDateTime).reversed());

        if (dateTimeFilter != null)
            mealStream = filterStream(mealStream, dateTimeFilter);

        return mealStream.collect(Collectors.toList());


    }

    private Stream<Meal> filterStream(Stream<Meal> stream, DateTimeFilter dateTimeFilter) {
        return stream.filter(meal -> DateTimeUtil.isBetween(meal.getDate(), dateTimeFilter.getStartDate(), dateTimeFilter.getEndDate())).
                filter(meal -> DateTimeUtil.isBetween(meal.getTime(), dateTimeFilter.getStartTime(), dateTimeFilter.getEndTime()));
    }

}

