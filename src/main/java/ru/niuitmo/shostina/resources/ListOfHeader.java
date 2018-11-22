package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.dataSets.ParamsDataSet;

import java.util.ArrayList;
import java.util.List;

public class ListOfHeader {
    List<String> tasks;

    public ListOfHeader(){};

    public ListOfHeader(List<String> tasks) {
        this.tasks = tasks;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
}
