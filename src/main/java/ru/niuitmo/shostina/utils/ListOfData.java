package ru.niuitmo.shostina.utils;

import java.util.List;

public class ListOfData {
    List<Data> data;

    ListOfData() {
    }

    public ListOfData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
