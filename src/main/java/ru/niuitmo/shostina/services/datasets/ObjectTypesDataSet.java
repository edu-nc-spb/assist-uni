package ru.niuitmo.shostina.services.datasets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "object_types")
public class ObjectTypesDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "object_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int objectTypeId;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "objectType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObjectsDataSet> objects;

    public ObjectTypesDataSet() {
    }

    public ObjectTypesDataSet(int id, String name) {
        this.objectTypeId = id;
        this.name = name;
    }

    public ObjectTypesDataSet(String name) {
        this.objectTypeId = -1;
        this.name = name;
    }

    public int getId() {
        return objectTypeId;
    }

    public void setId(int id) {
        this.objectTypeId = id;
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
