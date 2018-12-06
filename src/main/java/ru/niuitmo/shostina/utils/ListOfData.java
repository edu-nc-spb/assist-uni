package ru.niuitmo.shostina.utils;

import java.util.List;

public class ListOfData {
    List<DataElement> data;

    ListOfData() {
    }

    public ListOfData(List<DataElement> data) {
        this.data = data;
    }

    public List<DataElement> getData() {
        return data;
    }

    public void setData(List<DataElement> data) {
        this.data = data;
    }
}
