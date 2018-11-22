package ru.niuitmo.shostina.services;

public class Task {
    private String header;
    private String problem;
    private long id;
    public Task(){};
    public Task(String header, String problem) {
        this.header = header;
        this.problem = problem;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getHeader() {
        return header;
    }

    public String getProblem() {
        return problem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
