package ru.niuitmo.shostina.utils;

import java.util.Date;

public class Task {
    long id;
    String header;
    String problem;
    Date deadline;

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadine) {
        this.deadline = deadine;
    }
}
