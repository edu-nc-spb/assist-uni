package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "object_types")
public class ObjectTypesDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true, updatable = false)
    private String name;

    @Column(name = "parent_id", unique = true, updatable = false)
    private int parent_id;

    @SuppressWarnings("UnusedDeclaration")
    public ObjectTypesDataSet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
