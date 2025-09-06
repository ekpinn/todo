package com.example.todo.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TaskId implements Serializable {
    private int year;
    private int month;
    private int day;

    public TaskId() {}

    public TaskId(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskId taskId = (TaskId) o;
        return year == taskId.year && month == taskId.month && day == taskId.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}