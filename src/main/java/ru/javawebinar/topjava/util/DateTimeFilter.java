package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeFilter {

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private DateTimeFilter() {
    }

    public DateTimeFilter(String startDate, String endDate, String startTime, String endTime) {

        this.startDate = startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
        this.endDate = endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);

        this.startTime = startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
        this.endTime = endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
    }

//
//    public DateTimeFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


}