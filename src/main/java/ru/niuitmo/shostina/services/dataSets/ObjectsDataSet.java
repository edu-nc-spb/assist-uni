package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "objects")
public class ObjectsDataSet implements Serializable{
    private static final long serialVersionUID = -8706689714326132798L;
    @Id
    @Column(name = "object_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long object_id;

    @ManyToOne
    @JoinColumn(name="object_types_id")
    private ObjectTypesDataSet object_type;

    @OneToMany(mappedBy = "object", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParamsDataSet> params;

    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name="parent_id")
    private ObjectsDataSet parent;

    @OneToMany(mappedBy="parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<ObjectsDataSet> children;

    public ObjectsDataSet() {
    }

    public ObjectsDataSet(long id) {
        this.setObject_id(id);
    }


    public ObjectTypesDataSet getObject_type() {

        return object_type;
    }

    public void setObject_type(ObjectTypesDataSet object_type) {

        this.object_type = object_type;
    }

    public List<ParamsDataSet> getParams() {
        return params;
    }

    public void setParams(List<ParamsDataSet> params) {
        this.params = params;
    }

    public long getObject_id() {
        return object_id;
    }

    public void setObject_id(long object_id) {
        this.object_id = object_id;
    }

    public ObjectsDataSet getParent() {
        return parent;
    }

    public void setParent(ObjectsDataSet parent) {
        this.parent = parent;
    }

    public List<ObjectsDataSet> getChildren() {
        return children;
    }

    public void setChildren(List<ObjectsDataSet> children) {
        this.children = children;
    }
}
