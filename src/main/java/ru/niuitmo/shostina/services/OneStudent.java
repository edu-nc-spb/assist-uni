package ru.niuitmo.shostina.services;

public class OneStudent {
    String name;
    int id;
    public OneStudent(){};
    public OneStudent(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
