package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.niuitmo.shostina.services.dao.ObjectTypesDAO;
import ru.niuitmo.shostina.services.datasets.ObjectTypesDataSet;
import ru.niuitmo.shostina.services.datasets.ObjectsDataSet;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;

import java.util.ArrayList;
import java.util.List;

public class DBInitService extends ServiceUtils {

    public void initEntry() {
        TaskService taskService = new TaskService();

        try {
            addType(TEACHER);
            addType(STUDENT);
            long idT = addUser("Teacher1", TEACHER, "teacher1", "t1");
            long idS = addUser("Student1", STUDENT, "student1", "s1");
            addUser("Teacher2", TEACHER, "teacher2", "t2");
            addUser("Student2", STUDENT, "student2", "s2");

            addType(TASK);

            long idTask = taskService.addTask("Header1", "Problem1");
            taskService.assignTask(idT, idS, idTask);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void addType(String type) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new ObjectTypesDataSet(type));
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    private long addUser(String name, String type, String login, String password) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectTypesDataSet objectType = new ObjectTypesDAO(session).getByName(type);
            List<ParamsDataSet> params = new ArrayList<>();
            ObjectsDataSet object = new ObjectsDataSet();
            params.add(createParam(session, object, NAME, name));
            params.add(createParam(session, object, LOGIN, login));
            params.add(createParam(session, object, PASS, password));
            object.setParams(params);
            object.setObjectType(objectType);
            objectType.getObjects().add(object);
            long res = (long) session.save(object);
            session.update(objectType);
            transaction.commit();
            session.close();
            return res;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

}
