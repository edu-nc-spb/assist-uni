package ru.niuitmo.shostina.services.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.criterion.Restrictions;
import ru.niuitmo.shostina.services.dataSets.ObjectTypesDataSet;

public class ObjectTypesDAO {
    private Session session;

    public ObjectTypesDAO(Session session) {
        this.session = session;
    }

    public ObjectTypesDAO get(long id) throws HibernateException {
        return (ObjectTypesDAO) session.get(ObjectTypesDAO.class, id);
    }

    public ObjectTypesDataSet get(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(ObjectTypesDataSet.class);
        ObjectTypesDataSet res = (ObjectTypesDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult();
        if(res == null) {
            throw new HibernateException("wrong name");
        }
        return res;
    }
}
