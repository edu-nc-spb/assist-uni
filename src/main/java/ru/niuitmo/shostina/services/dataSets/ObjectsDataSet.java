package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "objects")
public class ObjectsDataSet implements Serializable{
    private static final long serialVersionUID = -8706689714326132798L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "object_type")
    private int object_type;

    @SuppressWarnings("UnusedDeclaration")
    public ObjectsDataSet() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public ObjectsDataSet(long id, int object_type) {
        this.setId(id);
        this.setObject_type(object_type);
    }

    public ObjectsDataSet(int object_type) {
        this.setId(-1);
        this.setObject_type(object_type);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getObject_type() {
        return object_type;
    }

    public void setObject_type(int object_type) {
        this.object_type = object_type;
    }
}
