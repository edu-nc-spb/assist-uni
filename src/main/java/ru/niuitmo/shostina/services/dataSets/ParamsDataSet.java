package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "params")
public class ParamsDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "object_id")
    private long object_id;

    @Column(name = "attr_id")
    private long attr_id;

    @Column(name = "attr_type")
    private int attr_type;

    @Column(name = "text_value")
    private String text_value;

    @SuppressWarnings("UnusedDeclaration")
    public ParamsDataSet() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public ParamsDataSet(long id, long object_id, long attr_id, int attr_type, String text_value) {
        this.setId(id);
        this.setObject_id(object_id);
        this.setAttr_id(attr_id);
        this.setAttr_type(attr_type);
        this.setText_value(text_value);
    }

    public ParamsDataSet(long object_id, long attr_id, int attr_type, String text_value) {
        this.setId(-1);
        this.setObject_id(object_id);
        this.setAttr_id(attr_id);
        this.setAttr_type(attr_type);
        this.setText_value(text_value);
    }

    public long getId() {
        return id;
    }

    public long getObject_id() {
        return object_id;
    }

    public long getAttr_id() {
        return attr_id;
    }

    public int getAttr_type() {
        return attr_type;
    }

    public String getText_value() {
        return text_value;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setObject_id(long object_id) {
        this.object_id = object_id;
    }

    public void setAttr_id(long attr_id) {
        this.attr_id = attr_id;
    }

    public void setAttr_type(int attr_type) {
        this.attr_type = attr_type;
    }

    public void setText_value(String text_value) {
        this.text_value = text_value;
    }
}
