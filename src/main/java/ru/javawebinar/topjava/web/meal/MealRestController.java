package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.LocalDateFormatterISO;
import ru.javawebinar.topjava.util.LocalTimeFormatterISO;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {

        Meal created = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }


    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List getBetween(@RequestParam(value = "startDate") String startDate, @RequestParam(value = "startTime") String startTime,
                           @RequestParam(value = "endDate") String endDate, @RequestParam(value = "endTime") String endTime) {

        LocalDateFormatterISO dateFormatterISO = new LocalDateFormatterISO();
        LocalTimeFormatterISO timeFormatterISO = new LocalTimeFormatterISO();

        LocalDate startDateFormatted;
        LocalDate endDateFormatted;

        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            startDateFormatted = LocalDate.MIN;
            endDateFormatted = LocalDate.MAX;
        } else {
            startDateFormatted = dateFormatterISO.parse(startDate, Locale.getDefault());
            endDateFormatted = dateFormatterISO.parse(endDate, Locale.getDefault());
        }

        LocalTime startTimeFormatted;
        LocalTime endTimeFormatted;

        if (startTime == null || endTime == null || startTime.isEmpty() || endTime.isEmpty()) {

            startTimeFormatted = LocalTime.MIN;
            endTimeFormatted = LocalTime.MAX;

        } else {
            startTimeFormatted = timeFormatterISO.parse(startTime, Locale.getDefault());
            endTimeFormatted = timeFormatterISO.parse(endTime, Locale.getDefault());
        }

        return super.getBetween(startDateFormatted, startTimeFormatted, endDateFormatted, endTimeFormatted);
    }
}