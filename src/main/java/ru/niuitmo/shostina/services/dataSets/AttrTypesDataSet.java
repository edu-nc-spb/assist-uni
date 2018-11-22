package ru.niuitmo.shostina.services.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attr_types")
public class AttrTypesDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @SuppressWarnings("UnusedDeclaration")
    public AttrTypesDataSet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
