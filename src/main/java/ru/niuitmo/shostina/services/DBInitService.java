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

    public static void main(String[] args) {
        new DBInitService().initEntry();
    }

    public void initEntry() {
        TaskService taskService = new TaskService();
        AssignedTaskService assignedTaskService = new AssignedTaskService();
        try {
            addType(TEACHER);
            addType(STUDENT);
            ArrayList<Long> students1 = new ArrayList<>();
            ArrayList<Long> students2 = new ArrayList<>();
            long idT1 = addUser("Корнеев Г.А.", TEACHER, "korneev", "t1");
            long idT2 = addUser("Елизаров Р.А.", TEACHER, "elizarov", "t2");
            long idT3 = addUser("Шалыто А.А.", TEACHER, "shal", "t3");
            students1.add(addUser("Волков", STUDENT, "student1", "s1"));
            students1.add(addUser("Глухов", STUDENT, "student2", "s2"));
            students1.add(addUser("Дугинец", STUDENT, "student3", "s3"));
            students1.add(addUser("Зеленов", STUDENT, "student4", "s4"));
            students1.add(addUser("Карлукова", STUDENT, "student5", "s5"));
            students1.add(addUser("Макеев", STUDENT, "student6", "s6"));
            students1.add(addUser("Наговицын", STUDENT, "student7", "s7"));
            students2.add(addUser("Осипов", STUDENT, "student8", "s8"));
            students2.add(addUser("Пережогин", STUDENT, "student9", "s9"));
            students2.add(addUser("Попыркина", STUDENT, "student10", "s10"));
            students2.add(addUser("Россомахина", STUDENT, "student11", "s11"));
            students2.add(addUser("Савон", STUDENT, "student12", "s12"));
            students2.add(addUser("Сайфулин", STUDENT, "student13", "s13"));
            students2.add(addUser("Тельной", STUDENT, "student14", "s14"));
            students2.add(addUser("Тимошенко", STUDENT, "student15", "s15"));
            addUser("Угай", STUDENT, "student16", "s16");
            addUser("Баев", STUDENT, "student17", "s17");
            addUser("Байдюк", STUDENT, "student18", "s18");
            addUser("Бин", STUDENT, "student19", "s19");
            addUser("Борисов", STUDENT, "student20", "s20");
            addUser("Варламов", STUDENT, "student21", "s21");
            addUser("Вахрушев", STUDENT, "student22", "s22");
            addUser("Виноградов", STUDENT, "student23", "s23");
            addUser("Жевтяк", STUDENT, "student24", "s24");
            addUser("Звягинцева", STUDENT, "student25", "s25");
            addUser("Ионов", STUDENT, "student26", "s26");
            addUser("Кравченко", STUDENT, "student27", "s27");
            addUser("Крылов", STUDENT, "student28", "s28");
            addUser("Кузьмин", STUDENT, "student29", "s29");
            addUser("Кухтеня", STUDENT, "student30", "s30");
            addType(TASK);
            ArrayList<Long> tasks1 = new ArrayList<>();
            ArrayList<Long> tasks2 = new ArrayList<>();
            ArrayList<Long> tasks3 = new ArrayList<>();
            tasks1.add(taskService.addTask("31 декабря (100)", "С лёгкой руки Гейдара Алиева в Азербайджане 31 декабря отмечается национальный праздник — День солидарности именно таких людей во всём мире."));
            tasks1.add(taskService.addTask("31 декабря (200)", "31 декабря юные голландки старались управиться со всеми делами по дому до заката; в противном случае это радостное событие отодвигалось на неопределённый срок."));
            tasks1.add(taskService.addTask("31 декабря (300)", "В сказке Маршака «12 месяцев» капризная королева объявила, что вслед за 31 декабря наступит 32-е, затем 33-е, и так будет, пока ей не принесут именно это."));
            tasks1.add(taskService.addTask("31 декабря (400)", "В Чехии 31 декабря непременно подают на стол карпа с хреном и чечевицей. А вот никакая птица на столе не приветствуется: из дома может «улететь» это."));
            tasks1.add(taskService.addTask("31 декабря (500)", "Эта единица может добавляться ко всемирному координированному времени или 30 июня, или 31 декабря. Последний раз её добавили два года назад."));
            tasks2.add(taskService.addTask("Новый год (200)", "В 1932 году Георг V впервые в истории Британии сделал это."));
            tasks2.add(taskService.addTask("Новый год (400)", "Эта шведская группа собиралась назвать хит «Папочка, не напивайся на Рождество», но получился просто «Happy New Year» — «C Новым годом!»."));
            tasks2.add(taskService.addTask("Новый год (600)", "Три американца: Джералд Карр, Эдвард Гибсон и Уильям Поуг — первыми в истории встретили Новый год именно там."));
            tasks2.add(taskService.addTask("Новый год (800)", "В Японии на Новый год дарят детям именно это в красочном конвертике. Количество зависит от от возраста ребёнка; обычай называется отосидама."));
            tasks2.add(taskService.addTask("Новый год (1000)", "По традиции в Мьянме, встречая Новый год по лунному календарю, щедро обливают водой встречного и поперечного. Запрещено обливать только буддистских монахов и их."));
            tasks3.add(taskService.addTask( "1 января (300)", "Эту монетку реанимировали 1 января 1998 года, а сейчас 91 % россиян категорически против неё."));
            tasks3.add(taskService.addTask("1 января (600)", "Римский Папа Павел VI объявил 1 января международным праздником — Днём всемирных молитв именно об этом."));
            tasks3.add(taskService.addTask("1 января (900)", "По традиции, 1 января днём в Большом театре идёт этот спектакль."));
            tasks3.add(taskService.addTask("1 января (1200)", "1 января 1801 года Джузеппе Пиацци открыл этот астероид и назвал в честь богини плодородия. Недавно астероид признали карликовой планетой."));
            tasks3.add(taskService.addTask("1 января (1500)", "Этот высший законосовещательный орган был утверждён указом Александра I 1 января 1810 года; а возродился он 10 лет назад как совещательный орган при Президенте России."));

            ArrayList<String> answer = new ArrayList<>();
            answer.add("Азербайджанцев");
            answer.add("Замужество");
            answer.add("Подснежники");
            answer.add("Счастье");
            answer.add("Секунда (високосная или секунда координации)");

            for(long s : students1) {
                for(int i = 0; i < 5; i++) {
                    long id = taskService.assignTask(idT1, s, tasks1.get(i), 60*24*10);
                    assignedTaskService.addAnswer(s, id, answer.get(i));
                }
                for(long task : tasks2) {
                    taskService.assignTask(idT1, s, task, 60*24*15);
                }
            }
            for(long s : students2) {
                for(long task : tasks2) {
                    taskService.assignTask(idT1, s, task, 60*24*15);
                }
            }
            for(long s : students2) {
                for(long task : tasks3) {
                    taskService.assignTask(idT2, s, task, 60*24*20);
                }
            }
            for(long s : students1) {
                for(long task : tasks3) {
                    taskService.assignTask(idT2, s, task, 60*24*20);
                }
            }

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
            object.setName(name);
            long res = (long) session.save(object);
            params.add(createParam(session, object, LOGIN, login));
            params.add(createParam(session, object, PASS, password));
            object.setParams(params);
            object.setObjectType(objectType);
            objectType.getObjects().add(object);
            session.update(object);
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
