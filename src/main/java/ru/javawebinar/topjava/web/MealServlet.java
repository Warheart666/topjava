package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.crud.Crud;
import ru.javawebinar.topjava.crud.MealInMemCrudImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@WebServlet("/mealList")
public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    //    private List<Meal> mealList = null;
    private Crud<Meal, Integer> mealServletDao = new MealInMemCrudImpl();


    public MealServlet() {
        super();
        MealsUtil.initStaticData().forEach(meal -> mealServletDao.create(meal));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceededByCycle(mealServletDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealListWithExceed", mealWithExceeds);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("edit")) {
            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealServletDao.read(mealId);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("edit")) {

            String desc = req.getParameter("description");
            int cal = Integer.parseInt(req.getParameter("calories"));
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));

            Meal meal = new Meal(dateTime, desc, cal);
            meal.setId(Integer.parseInt(req.getParameter("mealId")));

            mealServletDao.update(meal);
            resp.sendRedirect("/mealList");
        }

    }
}
