package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.niuitmo.shostina.services.dao.ObjectTypesDAO;
import ru.niuitmo.shostina.services.dao.ObjectsDAO;
import ru.niuitmo.shostina.services.datasets.ObjectTypesDataSet;
import ru.niuitmo.shostina.services.datasets.ObjectsDataSet;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;
import ru.niuitmo.shostina.utils.DataElement;
import ru.niuitmo.shostina.utils.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TaskService extends ServiceUtils {

    public Task getTask(long id) throws ServiceException {
        System.out.println(id);
        try {
            Session session = SESSIONFACTORY.openSession();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet task = (objectsDAO.get(id));
            Task res = new Task();
            res.setId(id);
            List<ParamsDataSet> taskParams;
            if (task.getParent() == null) {
                taskParams = task.getParams();
            } else {
                taskParams = task.getParent().getParams();
            }
            res.setHeader(task.getName());
            for (ParamsDataSet param : taskParams) {
                if (param.getAttr().equals(PROBLEM)) {
                    res.setProblem(param.getTextValue());
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
            System.out.println(id);
            Session session = SESSIONFACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet task = (objectsDAO.get(id));
            List<ParamsDataSet> taskParams = task.getParams();
            ListIterator<ParamsDataSet> iter = taskParams.listIterator();
            while (iter.hasNext()) {
                ParamsDataSet curr = iter.next();
                if (curr.getAttr().equals(PROBLEM)) {
                    curr.setTextValue(newProblem);
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
            System.out.println(id);
            Session session = SESSIONFACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet task = (objectsDAO.get(id));
            if(task == null) {
                System.out.println("!!It's null!!");
            }
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

    public long addTask(String header, String problem) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            List<ParamsDataSet> params = new ArrayList<>();
            ObjectsDataSet object = new ObjectsDataSet();
            object.setName(header);
            long res = (long) session.save(object);
            ParamsDataSet param = new ParamsDataSet(PROBLEM, problem);
            param.setObject(object);
            params.add(param);
            session.save(param);
            object.setParams(params);
            ObjectTypesDataSet type = new ObjectTypesDAO(session).getByName(TASK);
            object.setObjectType(type);
            session.update(object);
            type.getObjects().add(object);
            session.update(type);
            transaction.commit();
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public List<DataElement> getAllTasks() throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            ObjectTypesDAO objectTypesDAO = new ObjectTypesDAO(session);
            List<ObjectsDataSet> objects = (objectTypesDAO.getByName(TASK)).getObjects();
            List<DataElement> res = new ArrayList<>();
            for (ObjectsDataSet object : objects) {
                res.add(new DataElement(object.getName(), object.getObjectId()));
            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public void assignTask(long idTeacher, long idStudent, long idTask) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet task = objectsDAO.get(idTask);
            ObjectsDataSet myTaskObject = new ObjectsDataSet();
            myTaskObject.setName(task.getName() + " teacher: " +
                    idTeacher + "student: " + idStudent);
            myTaskObject.setParent(task);
            session.save(myTaskObject);
            List<ParamsDataSet> params = new ArrayList<>();
            params.add(createParam(session, myTaskObject, ANSWER, "no answer"));

            ObjectsDataSet teacher = objectsDAO.get(idTeacher);
            ParamsDataSet asTeacher = new ParamsDataSet(ASTEACHER);
            asTeacher.setObject(myTaskObject);
            asTeacher.setRefObject(teacher);
            asTeacher.setTextValue(teacher.getName());
            teacher.getReferences().add(asTeacher);
            params.add(asTeacher);
            session.save(asTeacher);

            ObjectsDataSet student = objectsDAO.get(idStudent);
            ParamsDataSet asStudent = new ParamsDataSet(ASSTUDENT);
            asStudent.setObject(myTaskObject);
            asStudent.setRefObject(student);
            asStudent.setTextValue(student.getName());
            student.getReferences().add(asStudent);
            params.add(asStudent);
            session.save(asStudent);

            myTaskObject.setParams(params);
            session.update(myTaskObject);
            session.update(teacher);
            session.update(student);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

}
