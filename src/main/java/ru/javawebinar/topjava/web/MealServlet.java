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
import java.time.format.DateTimeFormatter;
import java.util.List;


@WebServlet("/mealList")
public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private Crud<Meal> mealServletDao = new MealInMemCrudImpl();


    @Override
    public void init() throws ServletException {
        super.init();
        MealsUtil.initStaticData().forEach(meal -> mealServletDao.create(meal));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceededByCycle(mealServletDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealListWithExceed", mealWithExceeds);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("edit")) { //редактирование

            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealServletDao.read(mealId);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("insert")) { //создание

            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);

        } else if (action.equalsIgnoreCase("delete")) {
            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealServletDao.read(mealId);
            mealServletDao.delete(meal);
            resp.sendRedirect("mealList");
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("edit")) { //редактирование

            String desc = req.getParameter("description");
            int cal = Integer.parseInt(req.getParameter("calories"));

            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Meal meal = new Meal(dateTime, desc, cal);
            meal.setId(Integer.parseInt(req.getParameter("mealId")));

            mealServletDao.update(meal);
            resp.sendRedirect("mealList");

        } else if (action.equalsIgnoreCase("insert")) {//создание

            String desc = req.getParameter("description");
            int cal = Integer.parseInt(req.getParameter("calories"));
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            Meal meal = new Meal(dateTime, desc, cal);

            mealServletDao.create(meal);
            resp.sendRedirect("mealList");
        }


    }
}
