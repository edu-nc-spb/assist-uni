package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ru.niuitmo.shostina.resources.Data;
import ru.niuitmo.shostina.services.dao.*;

import ru.niuitmo.shostina.services.dataSets.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DBService {
    private static final String HIBERNATE_SHOW_SQL = "true";
    private static final String HIBERNATE_HBM2DDL_AUTO = "update";
    //private static final String hibernate_hbm2ddl_auto = "create";
    private final SessionFactory sessionFactory;

    private static DBService dbService;

    public static DBService instance() {
        if (dbService == null) {
            dbService = new DBService();
            try {
                dbService.addType("teacher");
                long idT = dbService.addUser("Teacher1", "teacher");
                dbService.addType("student");
                long idS = dbService.addUser("Student1", "student");
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

    public void assignTask(long teacher_id, long student_id, long task_id) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            ParamsDAO paramsDAO = new ParamsDAO(session);
            ObjectTypesDAO typesDAO = new ObjectTypesDAO(session);
            ObjectsDAO objectsDAO = new ObjectsDAO(session);

            ObjectsDataSet teacher = new ObjectsDAO(session).get(teacher_id);
            List<ParamsDataSet> teacherParams = teacher.getParams();

            for (ListIterator<ParamsDataSet> iter = teacherParams.listIterator(); iter.hasNext(); ) {
                ParamsDataSet p = iter.next();
                if (p.getAttr().equals("mytasks")) {
                    p.getSet_of_values().add(objectsDAO.get(task_id));
                    session.update(p);
                    break;
                }
            }
            session.update(teacher);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

    public long addUser(String name, String type) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<ParamsDataSet> params = new ArrayList<>();
            ObjectsDataSet object = new ObjectsDataSet();
            ParamsDataSet param1 = new ParamsDataSet("name", name);
            ParamsDataSet param2 = new ParamsDataSet("mytasks", new ArrayList<ObjectsDataSet>());
            param1.setObject(object);
            param2.setObject(object);
            params.add(param1);
            params.add(param2);
            object.setParams(params);
            ObjectTypesDataSet objectType = new ObjectTypesDAO(session).getByName(type);
            object.setObject_type(objectType);

            session.save(param1);
            session.save(param2);
            long res = (long) session.save(object);
            session.update(objectType);
            transaction.commit();
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public long addTask(String header, String problem) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            List<ParamsDataSet> params = new ArrayList<>();
            ObjectsDataSet object = new ObjectsDataSet();
            System.out.println("!!!ADDTASK: " + header + " " + problem);
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
            System.out.println(res);
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public List<Data> getMyTasks(long id) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ObjectTypesDAO objectTypesDAO = new ObjectTypesDAO(session);

            ObjectsDataSet user = (new ObjectsDAO(session).get(id));
            List<Data> res = new ArrayList<>();
            List<ParamsDataSet> params = user.getParams();
            System.out.println(params);
            for (ParamsDataSet j : params) {
                System.out.println(j.getAttr());
                if (j.getAttr().equals("mytasks")) {
                    for (ObjectsDataSet k : j.getSet_of_values()) {
                        for (ParamsDataSet l : k.getParams()) {
                            if (l.getAttr().equals("header")) {
                                res.add(new Data(l.getText_value(), k.getObject_id()));
                            }
                        }
                    }
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
            List<ObjectsDataSet> objects = (objectTypesDAO.getByName("student")).getObjects();
            List<Data> res = new ArrayList<>();
            for (ObjectsDataSet i : objects) {
                List<ParamsDataSet> params = i.getParams();
                for (ParamsDataSet j : params) {
                    if (j.getAttr().equals("name")) {
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
            ObjectsDataSet object = (objectsDAO.get(id));
            Task res = new Task();
            res.setId(id);
            List<ParamsDataSet> params = object.getParams();
            for (ParamsDataSet j : params) {
                if (j.getAttr().equals("header")) {
                    res.setHeader(j.getText_value());
                }
                if (j.getAttr().equals("problem")) {
                    res.setProblem(j.getText_value());
                }

            }
            System.out.println(res);
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }
}
