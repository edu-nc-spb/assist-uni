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


    public ParamsDataSet() {
    }

    public ParamsDataSet(long id, String attr, String text_value) {
        this.setId(id);
        this.setAttr_id(attr);
        this.setText_value(text_value);
    }

    public ParamsDataSet(String attr, String text_value) {
        this.setId(-1);
        this.setAttr_id(attr);
        this.setText_value(text_value);
    }

    public long getId() {
        return param_id;
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

    public void setId(long id) {
        this.param_id = id;
    }

    public void setObject(ObjectsDataSet object) {
        this.object = object;
    }

    public void setAttr_id(String attr) {
        this.attr = attr;
    }

    public void setText_value(String text_value) {
        this.text_value = text_value;
    }
}
