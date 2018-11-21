package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "object_types")
public class ObjectTypesDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "object_types_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int object_types_id;

    @Column(name = "name", unique = true, updatable = false)
    private String name;

    @OneToMany(mappedBy = "object_type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObjectsDataSet> objects;

    public ObjectTypesDataSet() {
    }

    public ObjectTypesDataSet(int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public ObjectTypesDataSet(String name) {
        this.setId(-1);
        this.setName(name);
    }

    public int getId() {
        return object_types_id;
    }

    public void setId(int id) {
        this.object_types_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ObjectsDataSet> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsDataSet> objects) {
        this.objects = objects;
    }
}
