package ru.niuitmo.shostina.services.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;

import java.util.List;

public class ParamsDAO {
    private final Session session;

    public ParamsDAO(Session session) {
        this.session = session;
    }

    public ParamsDataSet get(long id) throws HibernateException {
        return (ParamsDataSet) session.get(ParamsDataSet.class, id);
    }

    public List<ParamsDataSet> getByValue(String value) throws HibernateException {
        Criteria criteria = session.createCriteria(ParamsDataSet.class);
        criteria.add(Restrictions.eq("textValue", value));
        return criteria.list();
    }

    public long add(long objectId, String attr, String textValue) {
        return (long)session.save(new ParamsDataSet(objectId, attr, textValue));
    }
}
