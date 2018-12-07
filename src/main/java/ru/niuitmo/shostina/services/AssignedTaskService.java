package ru.niuitmo.shostina.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.niuitmo.shostina.services.dao.ObjectsDAO;
import ru.niuitmo.shostina.services.datasets.ObjectsDataSet;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;
import ru.niuitmo.shostina.utils.DataElement;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class AssignedTaskService extends ServiceUtils {

    public List<DataElement> getMyTasks(long id) throws ServiceException {
        try {
            Session session = SESSIONFACTORY.openSession();
            ObjectsDataSet user = (new ObjectsDAO(session).get(id));
            List<DataElement> res = new ArrayList<>();
            List<ParamsDataSet> params = user.getReferences();
            String student = "";
            for (ParamsDataSet param : params) {
                ObjectsDataSet myTaskObject = param.getObject();

                for(ParamsDataSet myTaskParam : myTaskObject.getParams()) {
                    if (myTaskParam.getAttr().equals(ASSTUDENT)) {
                        student = myTaskParam.getTextValue();
                    }
                }

                ObjectsDataSet task = myTaskObject.getParent();
                List<ParamsDataSet> taskParams = task.getParams();
                for (ParamsDataSet taskParam : taskParams) {
                    if (taskParam.getAttr().equals(HEADER)) {
                        res.add(new DataElement("(" + student + ") " + taskParam.getTextValue(),
                                myTaskObject.getObjectId()));
                    }
                }

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
