package ru.niuitmo.shostina.services;

public class LocalTask {
    Task task;
    String answer = "";
    int idSudent;
    public LocalTask(){};
    public LocalTask(String header, String problem) {
        task = new Task(header, problem);
    }
    public LocalTask(Task task, int idStudent) {
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

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public void deleteAnswer() {
        answer="";
    }
    public String getHeader() {
        return task.getHeader();
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalTask other = (LocalTask) o;
        return this.task.getHeader().equals(other.getTask().getHeader()) && this.getIdSudent() == other.getIdSudent();
    }*/
}
