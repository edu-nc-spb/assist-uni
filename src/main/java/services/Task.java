package services;

public class Task {
    private String header;
    private String problem;
    Task(){};
    Task(String header, String problem) {
        this.header = header;
        this.problem = problem;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getHeader() {
        return header;
    }

    public String getProblem() {
        return problem;
    }


    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task other = (Task) o;
        return (this.header.equals(other.header) &&
                this.problem.equals(other.problem) &&
                this.subject.equals(other.subject));
    }*/
}
