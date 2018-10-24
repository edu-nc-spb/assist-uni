package services;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalTask {
    Task task;
    String answer = "";
    int idSudent;
    LocalTask(){};
    LocalTask(String header, String problem) {
        task = new Task(header, problem);
    }
    LocalTask(Task task, int idStudent) {
        this.task = task;
        this.idSudent = idStudent;
    }

    public int getIdSudent() {
        return idSudent;
    }

    public Task getTask() {
        return task;
    }


    public void setTask(Task task) {
        this.task = task;
    }

    public String getAnswer() {
        return answer;
    }

    void setAnswer(String answer) {
        this.answer = answer;
    }
    void deleteAnswer() {
        answer="";
    }
    String getHeader() {
        return task.getHeader();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalTask other = (LocalTask) o;
        return (this.task.getHeader().equals(other.getTask().getHeader()) && this.getIdSudent() == other.getIdSudent());
    }
}
