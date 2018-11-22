package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attributes")
public class AttributesDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "object_type")
    private int object_type;

    @Column(name = "attr_type")
    private int attr_type;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public AttributesDataSet() {
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

    public int getObject_type() {
        return object_type;
    }

    public void setObject_type(int object_type) {
        this.object_type = object_type;
    }

    public int getAttr_type() {
        return attr_type;
    }

    public void setAttr_type(int attr_type) {
        this.attr_type = attr_type;
    }
}
