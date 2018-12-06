package ru.niuitmo.shostina.utils;

public class DataElement {
    String data;
    long id;

    public DataElement() {
    }

    public DataElement(String data, long id) {
        this.data = data;
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
