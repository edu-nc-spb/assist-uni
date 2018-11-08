package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class TaskByStudents {
    private static TaskByStudents instance;
    private HashMap<String, ArrayList<LocalTask>> localTasks = new HashMap<>();
    private TaskByStudents(){};
    public static TaskByStudents getInstance() {
        if (instance == null) {
            instance = new TaskByStudents();
            ArrayList<LocalTask>a = new ArrayList<>();
            a.add(new LocalTask(ListOfTasks.getInstance().getTask("Header1"), 2));
            instance.localTasks.put("Header1", a);
        }
        return instance;
    }
    public ArrayList<String> get() {
        return new ArrayList<String>(localTasks.keySet());
    }
    public boolean contain(String header) {
        return localTasks.containsKey(header);
    }

    public boolean contain(int id, String header) {
        if(localTasks.containsKey(header)) {
            ArrayList<LocalTask> a = localTasks.get(header);
            for(LocalTask t: a) {
                if(t.getIdSudent() == id)
                    return true;
            }
        }
        return false;
    }

    public void addStudent(LocalTask task) {
        if(!this.getInstance().localTasks.containsKey(task.getHeader())) {
            ArrayList<LocalTask>a = new ArrayList<>();
            a.add(task);
            localTasks.put(task.getHeader(), a);
            //System.out.println(task.getIdSudent() + " " + task.getHeader());
            StudentByTasks.getInstance().add(task.getIdSudent(), task);
        } else {
            if(! this.getInstance().localTasks.get(task.getHeader()).contains(task)) {
                this.getInstance().localTasks.get(task.getHeader()).add(task);
                //System.out.println(task.getIdSudent() + " " + task.getHeader());
                StudentByTasks.getInstance().add(task.getIdSudent(), task);
            }
        }
    }

    public ArrayList<Integer> getStudents (String header) {
        ArrayList<Integer> res = new ArrayList<>();
        if(this.getInstance().localTasks.containsKey(header)) {
            for (LocalTask t : localTasks.get(header)) {
                res.add(t.getIdSudent());
            }
        }
        return res;
    }

    public ArrayList<LocalTask> get(String header) {
        return localTasks.get(header);
    }

    public LocalTask getTask(int id, String header) {
        ArrayList<LocalTask> a = localTasks.get(header);
        for(int i = 0; i < a.size(); i++) {
            LocalTask t = a.get(i);
            if(t.getIdSudent()==id)
                return t;
        }
        throw new NoSuchElementException();
    }
}
