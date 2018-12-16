package ru.niuitmo.shostina.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.niuitmo.shostina.services.datasets.ObjectsDataSet;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;

import java.util.Date;

public class ServiceUtils {

    public static final SessionFactory SESSIONFACTORY = DBService.SESSIONFACTORY;
    public static final String LOGIN = "login";
    public static final String PASS = "password";
    public static final String STUDENT = "student";
    public static final String TEACHER = "teacher";
    public static final String PROBLEM = "problem";
    public static final String TASK = "task";
    public static final String ASTEACHER = "assigned by teacher";
    public static final String ASSTUDENT = "assign for student";
    public static final String ANSWER = "answer";
    public static final String DEADLINE = "deadline";
    public final static int  ONE_MINUTE_IN_MILLIS = 60000;


    public static ParamsDataSet createParam(Session session, ObjectsDataSet object, String param, Date date) {
        ParamsDataSet newParam = new ParamsDataSet(param);
        newParam.setDate(date);
        newParam.setObject(object);
        session.save(newParam);
        return newParam;
    }

    public static ParamsDataSet createParam(Session session, ObjectsDataSet object, String param, String value) {
        ParamsDataSet newParam = new ParamsDataSet(param, value);
        newParam.setObject(object);
        session.save(newParam);
        return newParam;
    }
}
