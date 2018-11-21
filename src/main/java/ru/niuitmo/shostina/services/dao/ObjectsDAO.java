package ru.niuitmo.shostina.services.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.niuitmo.shostina.services.dataSets.ObjectTypesDataSet;
import ru.niuitmo.shostina.services.dataSets.ObjectsDataSet;
import ru.niuitmo.shostina.services.dataSets.ParamsDataSet;

import java.util.List;

public class ObjectsDAO {
    private Session session;

    public ObjectsDAO(Session session) {
        this.session = session;
    }

    public ObjectsDataSet get(long id) throws HibernateException {
        return (ObjectsDataSet) session.get(ObjectsDataSet.class, id);
    }

    public List<ObjectsDataSet> getObjectsByType(int object_type) throws HibernateException {
        Criteria criteria = session.createCriteria(ObjectsDataSet.class);
        return criteria.add(Restrictions.eq("object_type", object_type)).list();
    }
}
