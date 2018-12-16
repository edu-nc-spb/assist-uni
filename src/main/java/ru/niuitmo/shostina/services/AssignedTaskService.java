package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.niuitmo.shostina.services.dao.ObjectsDAO;
import ru.niuitmo.shostina.services.datasets.ObjectsDataSet;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;
import ru.niuitmo.shostina.models.DataElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class AssignedTaskService extends ServiceUtils {


    private List<DataElement> getMyTaskHelper
            (String param, List<ParamsDataSet> myTasksParams) throws ServiceException {
        String user = "";
        Date deadline = null;
        List<DataElement> res = new ArrayList<>();
        for (ParamsDataSet myTask : myTasksParams) {
            ObjectsDataSet myTaskObject = myTask.getObject();
            ObjectsDataSet task = myTaskObject.getParent();
            for (ParamsDataSet myTaskParam : myTaskObject.getParams()) {
                if (myTaskParam.getAttr().equals(param)) {
                    user = myTaskParam.getTextValue();
                }
            }
            res.add(new DataElement("(" + user + ") " + task.getName(),
                        myTaskObject.getObjectId()));
        }
        return res;
    }

    public List<DataElement> getMyTasks(long id) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            ObjectsDataSet user = (new ObjectsDAO(session).get(id));
            List<DataElement> res;
            List<ParamsDataSet> myTasksParams = user.getReferences();
            if (user.getObjectType().getName().equals(STUDENT)) {
                res = getMyTaskHelper(ASTEACHER, myTasksParams);
            } else {
                res = getMyTaskHelper(ASSTUDENT, myTasksParams);
            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public void addAnswer(long id, long idMyTask, String newAnswer) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            ObjectsDataSet myTaskObject = objectsDAO.get(idMyTask);
            List<ParamsDataSet> taskParams = myTaskObject.getParams();
            ListIterator<ParamsDataSet> iter = taskParams.listIterator();
            while (iter.hasNext()) {
                ParamsDataSet curr = iter.next();
                if (curr.getAttr().equals(ANSWER)) {
                    curr.setTextValue(newAnswer);
                }
                session.update(curr);
            }
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }

    public String showAnswer(long idMyTask) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            ObjectsDAO objectsDAO = new ObjectsDAO(session);
            String res = "";
            ObjectsDataSet myTaskObject = objectsDAO.get(idMyTask);
            List<ParamsDataSet> taskParams = myTaskObject.getParams();
            for (ParamsDataSet param : taskParams) {
                if (param.getAttr().equals(ANSWER)) {
                    res = param.getTextValue();
                    break;
                }
            }
            session.close();
            return res;
        } catch (HibernateException e) {
            throw new ServiceException(e);
        }
    }
}
