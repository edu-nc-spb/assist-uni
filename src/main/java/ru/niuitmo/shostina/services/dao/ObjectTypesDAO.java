package ru.niuitmo.shostina.services.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.criterion.Restrictions;
import ru.niuitmo.shostina.services.dataSets.ObjectTypesDataSet;
import ru.niuitmo.shostina.services.dataSets.ObjectsDataSet;
import ru.niuitmo.shostina.services.dataSets.ParamsDataSet;

import java.util.List;

public class ObjectTypesDAO {
    private Session session;

    public ObjectTypesDAO(Session session) {
        this.session = session;
    }

    public ObjectTypesDAO get(long id) throws HibernateException {
        return (ObjectTypesDAO) session.get(ObjectTypesDAO.class, id);
    }

    public ObjectTypesDataSet getById(int id) throws HibernateException {
        Criteria criteria = session.createCriteria(ObjectTypesDataSet.class);
        ObjectTypesDataSet res = (ObjectTypesDataSet) criteria.add(Restrictions.eq("id", id)).uniqueResult();
        if(res == null) {
            throw new HibernateException("wrong id");
        }
        return res;
    }

    public ObjectTypesDataSet getByName(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(ObjectTypesDataSet.class);
        ObjectTypesDataSet res = (ObjectTypesDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult();
        if(res == null) {
            throw new HibernateException("wrong name " + name);
        }
        return res;
    }

    public int add(String name) {
       return (int)session.save(new ObjectTypesDataSet(name));
    }

    public void addOb(String name, ObjectsDataSet e) throws HibernateException {
        Criteria criteria = session.createCriteria(ObjectTypesDataSet.class);
        ObjectTypesDataSet res = (ObjectTypesDataSet) criteria.add(Restrictions.eq(
                "name", name)).uniqueResult();
        res.getObjects().add(e);
        session.saveOrUpdate(res);
    }
}
