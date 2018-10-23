package services;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalTask {
    Task task;
    HashMap<String, String> studentToAnswer = new HashMap<>();
    LocalTask(String header, String problem) {
        task = new Task(header, problem);
    }
    LocalTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    void addStudent(String student) {
        studentToAnswer.put(student, "");
    }
    void addAnswer(String student, String answer) {
        studentToAnswer.replace(student, answer);
    }
    void deleteAnswer(String student) {
        studentToAnswer.replace(student, "");
    }
    String getHeader() {
        return task.getHeader();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalTask other = (LocalTask) o;
        return (this.task.getHeader().equals(other.getTask().getHeader()));
    }
}
