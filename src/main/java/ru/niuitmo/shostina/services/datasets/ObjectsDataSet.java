package ru.niuitmo.shostina.services.datasets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "objects")
public class ObjectsDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;
    @Id
    @Column(name = "object_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long objectId;

    @ManyToOne
    @JoinColumn(name = "object_type")
    private ObjectTypesDataSet objectType;

    @OneToMany(mappedBy = "refObject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParamsDataSet> references;


    @OneToMany(mappedBy = "object", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParamsDataSet> params;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent")
    private ObjectsDataSet parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObjectsDataSet> children;

    public ObjectsDataSet() {
    }

    public ObjectsDataSet(long id) {
        this.objectId = id;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public ObjectTypesDataSet getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectTypesDataSet objectType) {
        this.objectType = objectType;
    }

    public List<ParamsDataSet> getParams() {
        return params;
    }

    public void setParams(List<ParamsDataSet> params) {
        this.params = params;
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

    public List<ParamsDataSet> getReferences() {
        return references;
    }

    public void setReferences(List<ParamsDataSet> references) {
        this.references = references;
    }
}
