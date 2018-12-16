package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.niuitmo.shostina.services.dao.ObjectTypesDAO;
import ru.niuitmo.shostina.services.dao.ParamsDAO;
import ru.niuitmo.shostina.services.datasets.ObjectsDataSet;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;
import ru.niuitmo.shostina.models.DataElement;
import ru.niuitmo.shostina.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserService extends ServiceUtils {

    private User getUserHelper (List<ParamsDataSet> params, String password, ObjectsDataSet object)
        throws ServiceException {
        for (ParamsDataSet param : params) {
            if (param.getAttr().equals(PASS)) {
                if (param.getTextValue().equals(password)) {
                    int role = 0;
                    if (object.getObjectType().getName().equals(TEACHER)) {
                        role = 1;
                    } else if (object.getObjectType().getName().equals(STUDENT)) {
                        role = 2;
                    }
                    return new User(Long.toString(object.getObjectId()), role);
                } else
                    throw new ServiceException("Wrong password");
            }
        }
        throw new ServiceException("User without password");
    }

    public User getUser(String login, String password) throws ServiceException {
        Session session = SESSIONFACTORY.openSession();
        ParamsDAO paramsDAO = new ParamsDAO(session);
        List<ParamsDataSet> users = paramsDAO.getByValue(login);
        if (users.isEmpty()) {
            throw new ServiceException("wrong password");
        }
        if (users.size() > 1) {
            throw new ServiceException("not unique login");
        }
        ObjectsDataSet object = users.get(0).getObject();

        return getUserHelper(object.getParams(), password, object);
    }

    public List<DataElement> getStudents() throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            ObjectTypesDAO objectTypesDAO = new ObjectTypesDAO(session);
            List<ObjectsDataSet> objects = (objectTypesDAO.getByName(STUDENT)).getObjects();
            List<DataElement> res = new ArrayList<>();
            for (ObjectsDataSet object : objects) {
                res.add(new DataElement(object.getName(), object.getObjectId()));
            }
            System.out.println(res);
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

}
