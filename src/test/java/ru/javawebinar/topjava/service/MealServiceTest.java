package ru.javawebinar.topjava.service;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL1_ID, 666);
        MEAL1.setId(MEAL1_ID);
        assertMatch(meal, MEAL1);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL1_ID, 777);
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), MEAL2);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = mealService.getBetweenDates(LocalDate.of(2015, Month.MAY, 29), LocalDate.of(2015, Month.MAY, 31), SecurityUtil.authUserId());
        assertMatch(meals, Lists.list(MEAL1, MEAL2).stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()));
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = mealService.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), LocalDateTime.of(2015, Month.MAY, 30, 11, 0), SecurityUtil.authUserId());
        assertMatch(meals, mealService.get(MEAL1_ID, SecurityUtil.authUserId()));
    }

    @Test
    public void getAll() {
        List<Meal> all = mealService.getAll(SecurityUtil.authUserId());
        assertMatch(all, Lists.list(MEAL1, MEAL2).stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()));
    }

    @Test
    public void update() {
        Meal updated = mealService.get(MEAL1_ID, 666);
        updated.setCalories(666);
        updated.setDescription("asdasd");
        mealService.update(updated, SecurityUtil.authUserId());
        assertMatch(mealService.get(MEAL1_ID, SecurityUtil.authUserId()), updated);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 11, 0), "Завтрак", 500);
        Meal createdMeal = mealService.create(meal, SecurityUtil.authUserId());
        meal.setId(createdMeal.getId());
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), Lists.list(createdMeal, MEAL1, MEAL2).stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()));
    }
}