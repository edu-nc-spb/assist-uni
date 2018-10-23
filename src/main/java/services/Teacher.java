package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

@Path("/teacher")
public class Teacher {
    private ArrayList<LocalTask> myTasks = new ArrayList<>();
    int cur= 0;
    private ListOfTasks tasks;

    @Path("/get-all-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ListOfHeader getTasks() {
        ArrayList<String> t = tasks.getInstance().getHeader();
        System.out.println(t.toString());
        return new ListOfHeader(t);
    }


    @Path("/get-my-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ListOfHeader getMyTasks() {
        myTasks.add(new LocalTask("Header1", "Problem1"));
        ArrayList<String> res = new ArrayList<>();
        for(LocalTask t: myTasks) {
            res.add(t.getHeader());
        }
        //System.out.println(res.toString());
        return new ListOfHeader(res);
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTask(MultivaluedMap<String, String> formParams) throws NoSuchElementException {
        String header = formParams.get("header").get(0);
        if(tasks.getInstance().contain(header)) {
            return tasks.getInstance().getTask(header);
        }
        throw new NoSuchElementException();
    }

    //@Context
    //private HttpServletRequest httpRequest;
    @Path("/add-student")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    public StringJson addStudent(MultivaluedMap<String, String> formParams) {
        String header = formParams.get("task").get(0);
        String student = formParams.get("student").get(0);
        if (tasks.getInstance().contain(header)) {
            Task t = tasks.getInstance().getTask(header);
            if((t.getHeader()).equals(header)) {
                int j = myTasks.indexOf(t);
                if(j == -1) {
                    LocalTask tt = new LocalTask(t);
                    myTasks.add(tt);
                    j = myTasks.indexOf(tt);
                    myTasks.get(j).addStudent(student);
                } else {
                    myTasks.get(j).addStudent(student);
                }
                return new StringJson("OK. Student " + student + " was added.");
            }
        }
        //System.out.println(res.toString());
        return new StringJson("ERROR. This task '" + header + "' hasn't been created.");
    }

    @Path("/create-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    public StringJson createTask(MultivaluedMap<String, String> formParams) throws IOException {
        String header = formParams.get("header").get(0);
        String problem = formParams.get("problem").get(0);
//        tasks.getInstance().add(new Task("H1", "P1"));
        Task newTask = new Task(header, problem);
        if(tasks.getInstance().contain(newTask))
            return new StringJson("ERROR. Task '" + header + "' has been created.");
        tasks.getInstance().add(newTask);
        myTasks.add(new LocalTask(newTask));
        return new StringJson("OK. You create new task '" + header + "'.");
    }

    @Path("/change-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public StringJson changeTask(MultivaluedMap<String, String> formParams) {
        String header = formParams.get("header").get(0);
        String newProblem = formParams.get("newProblem").get(0);
        if (!tasks.getInstance().contain(header))
            return new StringJson("ERROR. Task '" + header + "' hasn't been created.");
        tasks.getInstance().getTask(header).setProblem(newProblem);
        return new StringJson("OK. You changed task '" + header + "'.");
    }


    @Path("/delete-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public StringJson deleteTask(MultivaluedMap<String, String> formParams) {
        String header = formParams.get("header").get(0);
        if (!tasks.getInstance().contain(header))
            return new StringJson("ERROR. Task '" + header + "' hasn't been created.");
        tasks.getInstance().delete(header);
        return new StringJson("OK. You deleted task '" + header + "'.");
    }

    static class ListOfHeader{
        ArrayList<String> tasks;

        public ListOfHeader(){};

        public ListOfHeader(ArrayList<String> tasks) {
            this.tasks = tasks;
        }

        public ArrayList<String> getTasks() {
            return tasks;
        }

        public void setTasks(ArrayList<String> tasks) {
            this.tasks = tasks;
        }
    }
}
