package services;

import java.util.ArrayList;

public class ListOfStudent {
    ArrayList<OneStudent> data = new ArrayList<>();

    private static ListOfStudent instance;


    public static ListOfStudent getInstance() {
        if (instance == null) {
            instance = new ListOfStudent();
            instance.data.add(new OneStudent("Vasya", 1));
            instance.data.add(new OneStudent("Nastya", 2));
            instance.data.add(new OneStudent("Kolya", 3));
        }
        return instance;
    }

    public ArrayList<OneStudent> getData() {
        return data;
    }

    public void setData(ArrayList<OneStudent> data) {
        this.data = data;
    }
}
