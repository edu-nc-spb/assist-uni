package ru.niuitmo.shostina.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class StudentByTasks {
    private static StudentByTasks instance;
    private HashMap<Integer, ArrayList<LocalTask>> localTasks = new HashMap<>();
    private StudentByTasks(){};
    public static StudentByTasks getInstance() {
        if (instance == null) {
            instance = new StudentByTasks();
            ArrayList<LocalTask>a = new ArrayList<>();
            a.add(new LocalTask(ListOfTasks.getInstance().getTask("Header1"), 2));
            instance.localTasks.put(2, a);
        }
        return instance;
    }
    public boolean contain(int id) {
        return localTasks.containsKey(id);
    }

    public boolean contain(int id, String header) {
        if(localTasks.containsKey(id)) {
            ArrayList<LocalTask> a = localTasks.get(id);
            for(LocalTask t: a) {
                if(t.getHeader().equals(header))
                    return true;
            }
        }
        return false;
    }

    public void add(int id, LocalTask task) {
        if(!this.getInstance().localTasks.containsKey(id)) {
            ArrayList<LocalTask>a = new ArrayList<>();
            a.add(task);
            localTasks.put(id, a);
            //System.out.println(id + " " + task.getHeader());
        } else {
            this.getInstance().localTasks.get(id).add(task);
        }
    }

    public ArrayList<String> getHeader(int id) {
        ArrayList<String> res = new ArrayList<>();
        if(this.getInstance().localTasks.containsKey(id)) {
            for (LocalTask t : localTasks.get(id)) {
                res.add(t.getHeader());
            }
        }
        return res;
    }

    public ArrayList<LocalTask> get(int id) {
       return localTasks.get(id);
    }
    public LocalTask getTask(int id, String header) {
        ArrayList<LocalTask> a = localTasks.get(id);
        for(int i = 0; i < a.size(); i++) {
        LocalTask t = a.get(i);
        if(t.getHeader().equals(header))
            return t;
        }
        throw new NoSuchElementException();
    }
}
