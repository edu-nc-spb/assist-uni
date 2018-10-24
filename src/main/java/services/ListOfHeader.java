package services;

import java.util.ArrayList;

public class ListOfHeader {
    ArrayList<String> tasks;

    public ListOfHeader(){};

    public ListOfHeader(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
    }
}
