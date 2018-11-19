package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ru.niuitmo.shostina.services.dao.ObjectsDAO;
import ru.niuitmo.shostina.services.dao.ParamsDAO;
import ru.niuitmo.shostina.services.dataSets.ObjectsDataSet;
import ru.niuitmo.shostina.services.dataSets.ParamsDataSet;

import java.util.ArrayList;
import java.util.List;

public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    //private static final String hibernate_hbm2ddl_auto = "create";
    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ParamsDataSet.class);
        configuration.addAnnotatedClass(ObjectsDataSet.class);

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

    public void addTask(String header, String problem) throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ParamsDAO dao = new ParamsDAO(session);
            dao.addTask(header, problem);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }
    public List<String> getAllTasks() throws ServiceException {
        try {
            Session session = sessionFactory.openSession();
            ParamsDAO paramsDAO = new ParamsDAO(session);
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            List<ObjectsDataSet> objects = objectsDAO.getObjectsByType(4);
            List<String> res = new ArrayList<>();
            for(ObjectsDataSet i : objects) {
                ParamsDataSet dataSet = paramsDAO.get(i.getId(), 4);
                res.add(dataSet.getText_value());
            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }
}
