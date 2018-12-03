package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.niuitmo.shostina.utils.Data;
import ru.niuitmo.shostina.services.dao.*;
import ru.niuitmo.shostina.services.dataSets.*;
import ru.niuitmo.shostina.utils.Task;
import ru.niuitmo.shostina.utils.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DBService {
    private static final String HIBERNATE_SHOW_SQL = "true";
    private static final String HIBERNATE_HBM2DDL_AUTO = "update";
    private final SessionFactory sessionFactory;
    private static DBService dbService;
    private final String NAME = "name";
    private final String LOGIN = "login";
    private final String PASS = "password";
    private final static String STUDENT = "student";
    private final static String TEACHER = "teacher";


    public static DBService instance() {
        if (dbService == null) {
            dbService = new DBService();
            try {
                dbService.addType(TEACHER);
                dbService.addType(STUDENT);
                long idT = dbService.addUser("Teacher1", TEACHER, "teacher1", "t1");
                long idS = dbService.addUser("Student1", STUDENT, "student1", "s1");
                dbService.addUser("Teacher2", TEACHER, "teacher2", "t2");
                dbService.addUser("Student2", STUDENT, "student2", "s2");

                dbService.addType("task");
                long idTask = dbService.addTask("Header1", "Problem1");
                dbService.assignTask(idT, idS, idTask);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        return dbService;
    }

    public DBService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ParamsDataSet.class);
        configuration.addAnnotatedClass(ObjectsDataSet.class);
        configuration.addAnnotatedClass(ObjectTypesDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public User checkUser(String login, String password) throws ServiceException {
        Session session = sessionFactory.openSession();
        ObjectsDAO objectsDAO = new ObjectsDAO(session);
        ParamsDAO paramsDAO = new ParamsDAO(session);
        List<ParamsDataSet> users = paramsDAO.getByValue(login);
        if (users.size() == 0) {
            throw new ServiceException("wrong password");
        }
        if (users.size() > 1) {
            throw new ServiceException("not unique login");
        }
        ObjectsDataSet object = users.get(0).getObject();
        List<ParamsDataSet> usersParams = object.getParams();
        for (ParamsDataSet p : usersParams) {
            if (p.getAttr().equals(PASS)) {
                if (p.getText_value().equals(password)) {
                    int role = 0;
                    if (object.getObject_type().getName().equals(TEACHER)) {
                        role = 1;
                    } else if (object.getObject_type().getName().equals(STUDENT)) {
                        role = 2;
                    }
                    return new User(Long.toString(object.getObject_id()), role);
                }
                else
                    throw new ServiceException("wrong password");
            }
        }
        throw new ServiceException("wrong password");
    }

    public void addType(String type) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new ObjectTypesDataSet(type));
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public void assignTask(long idTeacher, long idStudent, long idTask) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet myTaskObject = new ObjectsDataSet();
            myTaskObject.setParent(objectsDAO.get(idTask));
            ParamsDataSet answer = new ParamsDataSet("answer", "no answer");
            answer.setObject(myTaskObject);
            List<ParamsDataSet> params = new ArrayList<>();
            params.add(answer);
            myTaskObject.setParams(params);
            session.save(answer);
            long idMyTask = (long) session.save(myTaskObject);
            ObjectsDataSet teacher = objectsDAO.get(idTeacher);
            ParamsDataSet myTaskParam = new ParamsDataSet("mytask", idMyTask);
            myTaskParam.setObject(teacher);
            teacher.getParams().add(myTaskParam);
            session.save(myTaskParam);
            session.update(teacher);
            ObjectsDataSet student = objectsDAO.get(idStudent);
            myTaskParam = new ParamsDataSet("mytask", idMyTask);
            myTaskParam.setObject(student);
            student.getParams().add(myTaskParam);
            session.save(myTaskParam);
            session.update(student);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

    private ParamsDataSet createParam(Session session, ObjectsDataSet object, String param, String value) {
        ParamsDataSet newParam = new ParamsDataSet(param, value);
        newParam.setObject(object);
        session.save(newParam);
        return newParam;
    }

    public long addUser(String name, String type, String login, String password) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<ParamsDataSet> params = new ArrayList<>();
            ObjectsDataSet object = new ObjectsDataSet();

            ParamsDataSet param1 = new ParamsDataSet("name", name);
            param1.setObject(object);
            params.add(param1);
            ParamsDataSet param2 = new ParamsDataSet("login", login);
            param2.setObject(object);
            ParamsDataSet param3 = new ParamsDataSet("password", password);
            param3.setObject(object);

            object.setParams(params);
            ObjectTypesDataSet objectType = new ObjectTypesDAO(session).getByName(type);
            object.setObject_type(objectType);

            session.save(param1);
            session.save(param2);
            session.save(param3);
            long res = (long) session.save(object);
            session.update(objectType);
            transaction.commit();
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    /*public long addUser(String name, String type, String login, String password) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<ParamsDataSet> params = new ArrayList<>();
            ObjectsDataSet object = new ObjectsDataSet();
            params.add(createParam(session, object, NAME, name));
            params.add(createParam(session, object, LOGIN, login));
            params.add(createParam(session, object, PASS, password));
            object.setParams(params);
            ObjectTypesDataSet objectType = new ObjectTypesDAO(session).getByName(type);
            object.setObject_type(objectType);
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
    }*/

    public long addTask(String header, String problem) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<ParamsDataSet> params = new ArrayList<>();
            ObjectsDataSet object = new ObjectsDataSet();
            ParamsDataSet param1 = new ParamsDataSet("header", header);
            ParamsDataSet param2 = new ParamsDataSet("problem", problem);
            param1.setObject(object);
            param2.setObject(object);
            params.add(param1);
            params.add(param2);
            object.setParams(params);
            ObjectTypesDataSet type = new ObjectTypesDAO(session).getByName("task");
            object.setObject_type(type);
            type.getObjects().add(object);
            session.save(param1);
            session.save(param2);
            long res = (long) session.save(object);
            session.update(type);
            transaction.commit();
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public List<Data> getAllTasks() throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ObjectTypesDAO objectTypesDAO = new ObjectTypesDAO(session);
            List<ObjectsDataSet> objects = (objectTypesDAO.getByName("task")).getObjects();
            List<Data> res = new ArrayList<>();
            for (ObjectsDataSet i : objects) {
                List<ParamsDataSet> params = i.getParams();
                for (ParamsDataSet j : params) {
                    if (j.getAttr().equals("header")) {
                        res.add(new Data(j.getText_value(), i.getObject_id()));
                        break;
                    }
                }
            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public List<Data> getMyTasks(long id) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet user = (new ObjectsDAO(session).get(id));
            List<Data> res = new ArrayList<>();
            List<ParamsDataSet> params = user.getParams();
            for (ParamsDataSet j : params) {
                if (j.getAttr().equals("mytask")) {
                    ObjectsDataSet myTaskObject = objectsDAO.get(j.getNum_value());
                    ObjectsDataSet task = myTaskObject.getParent();
                    List<ParamsDataSet> taskParams = task.getParams();
                    for (ParamsDataSet i : taskParams) {
                        if (i.getAttr().equals("header")) {
                            res.add(new Data(i.getText_value(), myTaskObject.getObject_id()));
                        }
                    }
                }
            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public String showAnswer(long idTeacher, long idStudent, long idMyTask) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            String res = "";
            ObjectsDataSet myTaskObject = objectsDAO.get(idMyTask);
            List<ParamsDataSet> taskParams = myTaskObject.getParams();
            for (ParamsDataSet p : taskParams) {
                if (p.getAttr().equals("answer")) {
                    res = p.getText_value();
                    break;
                }
            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public List<Data> getStudents() throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ObjectTypesDAO objectTypesDAO = new ObjectTypesDAO(session);
            List<ObjectsDataSet> objects = (objectTypesDAO.getByName(STUDENT)).getObjects();
            List<Data> res = new ArrayList<>();
            for (ObjectsDataSet i : objects) {
                List<ParamsDataSet> params = i.getParams();
                for (ParamsDataSet j : params) {
                    if (j.getAttr().equals(NAME)) {
                        res.add(new Data(j.getText_value(), j.getObject().getObject_id()));
                        break;
                    }
                }
            }
            System.out.println(res);
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public Task getTask(long id) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet task = (objectsDAO.get(id));
            Task res = new Task();
            res.setId(id);
            List<ParamsDataSet> taskParams;
            if (task.getParent() != null) {
                taskParams = task.getParent().getParams();
            } else {
                taskParams = task.getParams();
            }
            for (ParamsDataSet j : taskParams) {
                if (j.getAttr().equals("header")) {
                    res.setHeader(j.getText_value());
                }
                if (j.getAttr().equals("problem")) {
                    res.setProblem(j.getText_value());
                }

            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public void changeTask(long id, String newProblem) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet task = (objectsDAO.get(id));
            List<ParamsDataSet> taskParams = task.getParams();
            ListIterator<ParamsDataSet> iter = taskParams.listIterator();
            while (iter.hasNext()) {
                ParamsDataSet curr = iter.next();
                if (curr.getAttr().equals("problem")) {
                    curr.setText_value(newProblem);
                }
                session.update(curr);
            }
            session.update(task);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteTask(long id) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet task = (objectsDAO.get(id));
            if (task.getChildren().size() > 0) {
                throw new ServiceException("Ошибка.Это задание назначено студенту, вы не можете его удалить");
            }
            session.delete(task);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    public void addAnswer(long id, long idMyTask, String newAnswer) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet myTaskObject = objectsDAO.get(idMyTask);
            List<ParamsDataSet> taskParams = myTaskObject.getParams();
            ListIterator<ParamsDataSet> iter = taskParams.listIterator();
            while (iter.hasNext()) {
                ParamsDataSet curr = iter.next();
                if (curr.getAttr().equals("answer")) {
                    curr.setText_value(newAnswer);
                }
                session.update(curr);
            }
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }
}
