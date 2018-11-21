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

    public ParamsDataSet getByAttr(long attr_id) throws HibernateException {
        Criteria criteria = session.createCriteria(ParamsDataSet.class);
        criteria.add(Restrictions.eq("attr_id", attr_id)).uniqueResult();
        ParamsDataSet res = (ParamsDataSet)criteria;
        return res;
    }

    public ParamsDataSet get(long object_id, long attr_id) throws HibernateException {
        Criteria criteria = session.createCriteria(ParamsDataSet.class);
        criteria.add(Restrictions.eq("object_id", object_id));
        criteria.add(Restrictions.eq("attr_id", attr_id));
        ParamsDataSet res = (ParamsDataSet)criteria.list().get(0);
        if(res == null) {
            throw new HibernateException("wrong data");
        }
        return res;
    }
    /*public void addTask(String header, String problem) {
        ObjectTypesDAO dao = new ObjectTypesDAO(session);
        dao.addOb("task");
        long id_object = (long)session.save(new ObjectsDataSet(4));
        System.out.println(header + " " + id_object);
        session.save(new ParamsDataSet(id_object, 4, 1, header));
        session.save(new ParamsDataSet(id_object, 5, 1, problem));
    }*/
    public long add(long object_id, String attr, String text_value) {
        return (long)session.save(new ParamsDataSet(object_id, attr, text_value));
    }
}
