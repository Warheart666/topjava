package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

class MealRestControllerTest extends AbstractControllerTest {

    private final static String REST_URL = "/rest/meals/";

    @Test
    void getTest() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));

    }

    @Test
    void deleteTest() throws Exception {

        mockMvc.perform(delete(REST_URL + (MEAL1_ID + 1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertEquals(mealService.getAll(UserTestData.USER_ID).size(), MealTestData.MEALS.size() - 1);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealTestData.MEAL1, MealTestData.MEAL2, MealTestData.MEAL3, MealTestData.MEAL4, MealTestData.MEAL5, MealTestData.MEAL6));

    }

    @Test
    void create() throws Exception {

        Meal expected = new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак", 500);
        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(resultActions, Meal.class);

        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(UserTestData.USER_ID), returned, MealTestData.MEAL6, MealTestData.MEAL5, MealTestData.MEAL4, MealTestData.MEAL3, MealTestData.MEAL2, MealTestData.MEAL1);

    }

    @Test
    void update() throws Exception {

        Meal updated = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updated.setDescription("New desc");
        updated.setCalories(666);
        mockMvc.perform(put(REST_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        assertMatch(mealService.get(MEAL1.getId(), UserTestData.USER_ID), updated);


    }

    @Test
    void getBetween() throws Exception {

        mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2015-05-30T00:00:00")
                .param("endDate", "2015-05-30T00:00:00")
                .param("startTime", "2012-12-12T09:30:00")
                .param("endTime", "2012-12-12T23:30:00"))
                .andDo(print())
                .andExpect(contentJson(MEAL1, MEAL2, MEAL3));

    }


}