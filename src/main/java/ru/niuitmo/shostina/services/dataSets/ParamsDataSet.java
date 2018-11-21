package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "params")
public class ParamsDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "param_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long param_id;

    @ManyToOne
    @JoinColumn (name = "object_id")
    private ObjectsDataSet object;

    @Column(name = "attr")
    private String attr;

    @Column(name = "text_value")
    private String text_value;


    @OneToMany(/*mappedBy="param_parent",*/ fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObjectsDataSet> set_of_values;

    public ParamsDataSet() {
    }

    public ParamsDataSet(long id, String attr, String text_value) {
        this.setParam_id(id);
        this.setAttr(attr);
        this.setText_value(text_value);
    }

    public ParamsDataSet(String attr, List<ObjectsDataSet> value) {
        this.setParam_id(-1);
        this.setAttr(attr);
        this.setSet_of_values(value);
    }

    public ParamsDataSet(String attr, String text_value) {
        this.setParam_id(-1);
        this.setAttr(attr);
        this.setText_value(text_value);
    }


    public ObjectsDataSet getObject() {
        return object;
    }

    public String getAttr() {
        return attr;
    }

    public String getText_value() {
        return text_value;
    }

    public void setObject(ObjectsDataSet object) {
        this.object = object;
    }

    public void setText_value(String text_value) {
        this.text_value = text_value;
    }

    public long getParam_id() {
        return param_id;
    }

    public void setParam_id(long param_id) {
        this.param_id = param_id;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public List<ObjectsDataSet> getSet_of_values() {
        return set_of_values;
    }

    public void setSet_of_values(List<ObjectsDataSet> set_of_values) {
        this.set_of_values = set_of_values;
    }
}
