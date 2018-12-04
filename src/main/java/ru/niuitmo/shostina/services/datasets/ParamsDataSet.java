package ru.niuitmo.shostina.services.datasets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "params")
public class ParamsDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "param_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paramId;

    @ManyToOne
    @JoinColumn (name = "object_id")
    private ObjectsDataSet object;

    @Column(name = "attr")
    private String attr;

    @Column(name = "text_value")
    private String textValue;


    @Column(name="num_value")
    private long numValue;

    public ParamsDataSet() {
    }

    public ParamsDataSet(long id, String attr, String textValue) {
        this.paramId = id;
        this.attr = attr;
        this.textValue = textValue;
    }

    public ParamsDataSet(String attr, String textValue) {
        this.paramId = -1;
        this.attr = attr;
        this.textValue = textValue;
    }

    public ParamsDataSet(long id, String attr, long numValue) {
        this.paramId = id;
        this.attr = attr;
        this.numValue = numValue;
    }

    public ParamsDataSet(String attr, long numValue) {
        this.paramId = -1;
        this.attr = attr;
        this.numValue = numValue;
    }

    public ObjectsDataSet getObject() {
        return object;
    }

    public String getAttr() {
        return attr;
    }

    public void setObject(ObjectsDataSet object) {
        this.object = object;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public long getNumValue() {
        return numValue;
    }

    public void setNumValue(long numValue) {
        this.numValue = numValue;
    }

    public long getParamId() {
        return paramId;
    }

    public void setParamId(long paramId) {
        this.paramId = paramId;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
}
