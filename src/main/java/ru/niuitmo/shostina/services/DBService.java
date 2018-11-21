package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ru.niuitmo.shostina.services.dao.*;

import ru.niuitmo.shostina.services.dataSets.*;

import java.util.ArrayList;
import java.util.List;

public class DBService {
    private static final String hibernate_show_sql = "true";
    //private static final String hibernate_hbm2ddl_auto = "update";
    private static final String hibernate_hbm2ddl_auto = "create";
    private final SessionFactory sessionFactory;

    private static DBService dbService;

    public static DBService instance(){
        if (dbService == null) {
            dbService = new DBService();
            try {
                dbService.addType("task");
                dbService.addTask("Header1", "Problem1");
            } catch (ServiceException e){
                System.out.println(e.getMessage());
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
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public ParamsDataSet getParamsById(long id) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ParamsDAO dao = new ParamsDAO(session);
            ParamsDataSet dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
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

    public void addTask(String header, String problem) throws ServiceException {
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
            session.save(object);
            session.update(type);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }
    public List<String> getAllTasks() throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ObjectTypesDAO objectTypesDAO= new ObjectTypesDAO(session);
            List<ObjectsDataSet>objects = (objectTypesDAO.getByName("task")).getObjects();
            List<String> res = new ArrayList<>();
            for(ObjectsDataSet i : objects) {
                List<ParamsDataSet> params = i.getParams();
                for(ParamsDataSet j : params) {
                    if(j.getAttr().equals("header")) {
                        res.add(j.getText_value());
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
}
