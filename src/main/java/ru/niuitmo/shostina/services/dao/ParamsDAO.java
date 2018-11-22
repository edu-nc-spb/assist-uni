package ru.niuitmo.shostina.services.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.niuitmo.shostina.services.dataSets.ObjectsDataSet;
import ru.niuitmo.shostina.services.dataSets.ParamsDataSet;

public class ParamsDAO {
    private Session session;

    public ParamsDAO(Session session) {
        this.session = session;
    }

    public ParamsDataSet get(long id) throws HibernateException {
        return (ParamsDataSet) session.get(ParamsDataSet.class, id);
    }

    public ParamsDataSet getByAttr(String attr) throws HibernateException {
        Criteria criteria = session.createCriteria(ParamsDataSet.class);
        criteria.add(Restrictions.eq("attr", attr)).uniqueResult();
        ParamsDataSet res = (ParamsDataSet)criteria;
        return res;
    }

    public long add(long object_id, String attr, String text_value) {
        return (long)session.save(new ParamsDataSet(object_id, attr, text_value));
    }
}
