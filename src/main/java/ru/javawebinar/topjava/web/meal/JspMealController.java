package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(path = "/meals")
public class JspMealController extends AbstractMealController {


    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }


    @GetMapping("/delete&id={id}")
    public String remove(@PathVariable("id") int id) {
        service.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String getForCreate(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("action", "create");
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update&id={id}")
    public String getForUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @PostMapping
    public String filter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }


    @PostMapping("/update")
    public String update(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String dsc = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));

        Meal meal = super.get(id);

        meal.setDateTime(localDateTime);
        meal.setDescription(dsc);
        meal.setCalories(calories);

        super.update(meal, id);
        return "redirect:/meals";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest request) {

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String dsc = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));

        Meal meal = new Meal();
        meal.setDateTime(localDateTime);
        meal.setDescription(dsc);
        meal.setCalories(calories);

        super.create(meal);
        return "redirect:/meals";
    }


}
