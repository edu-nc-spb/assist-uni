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

    public ObjectsDataSet() {
    }

    public ObjectsDataSet(long id) {
        this.setId(id);
    }

    public long getId() {
        return object_id;
    }

    public void setId(long id) {
        this.object_id = id;
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
}
