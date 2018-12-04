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
    @JoinColumn (name = "objectId")
    private ObjectsDataSet object;

    @Column(name = "attr")
    private String attr;

    @Column(name = "text_value")
    private String textValue;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private ObjectsDataSet refObject;

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

    public ParamsDataSet(long id, String attr) {
        this.paramId = id;
        this.attr = attr;
    }

    public ParamsDataSet(String attr) {
        this.paramId = -1;
        this.attr = attr;
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

    public ObjectsDataSet getRefObject() {
        return refObject;
    }

    public void setRefObject(ObjectsDataSet refObject) {
        this.refObject = refObject;
    }
}
