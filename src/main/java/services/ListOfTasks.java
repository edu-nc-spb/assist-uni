package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ListOfTasks {
        private static ListOfTasks instance;
        private ArrayList<Task> tasks = new ArrayList<>();
        private ListOfTasks(){};
        public static ListOfTasks getInstance() {
            if (instance == null) {
                instance = new ListOfTasks();
                instance.tasks.add(new Task("Header1", "Problem1"));
            }
            return instance;
        }
        protected boolean contain(Task task) {
            for(Task t : tasks) {
                if(t.getHeader().equals(task.getHeader())) {
                    return true;
                }
            }
            return false;
        }

    protected boolean contain(String header) {
        for(Task t : tasks) {
            if(t.getHeader().equals(header)) {
                return true;
            }
        }
        return false;
    }

        public void add(Task task) throws IOException{
            if(!this.contain(task)) {
                tasks.add(task);
            } else {
                throw new IOException("Header '" + task.getHeader() + "' isn't unique.");
            }
        }
        public ArrayList<String> getHeader() {

            ArrayList<String> res = new ArrayList<>();
            for(Task t: tasks) {
                res.add(t.getHeader());
            }
            return res;
        }
    public Task getTask(String header) {
        for(int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getHeader().equals(header)) {
                return tasks.get(i);
            }
        }
        throw new NoSuchElementException();
    }

    public void delete(String header) {
        if(contain(header)) {
            instance.tasks.remove(getTask(header));
        } else {
            throw new NoSuchElementException();
        }
    }
}
