
package resources;

import services.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Path("/teacher/{id}")
public class Teacher {
    private TaskByStudents myTasks;
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
        ArrayList<String> res = new ArrayList<>();
        return new ListOfHeader(myTasks.getInstance().get());
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTask(MultivaluedMap<String, String> formParams) throws NoSuchElementException {
        String header = formParams.get("header").get(0);
        System.out.println("get " + header);
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
    public StringJson addStudent(@PathParam("id")int id, MultivaluedMap<String, String> formParams) throws IOException {
        String header = formParams.get("header").get(0);
        int idStudent  = Integer.parseInt(formParams.get("id").get(0));
        if(tasks.getInstance().contain(header)) {
            myTasks.getInstance().addStudent(new LocalTask(tasks.getInstance().getTask(header), idStudent));
            return new StringJson("OK. Task '"+ header + "' was added for student '" + idStudent + "'.");
        } else
            return new StringJson("Error. You don't have task '" + header + "'.");
    }

    @Path("/show-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public StringJson showAnswer(@PathParam("id")int id, MultivaluedMap<String, String> formParams) throws IOException {
        String header = formParams.get("header").get(0);
        int idStudent  = Integer.parseInt(formParams.get("id").get(0));
        if(myTasks.getInstance().contain(idStudent, header)) {
            return new StringJson("OK. Answer for task '"+ header + "' by student '" + idStudent + "' is: '"
            + myTasks.getInstance().getTask(idStudent, header).getAnswer() + "'.");
        } else
            return new StringJson("Error. You don't have task '" + header + "'.");
    }

    @Path("/create-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    public StringJson createTask(MultivaluedMap<String, String> formParams) throws IOException {
        System.out.println(formParams.toString());
        String header = formParams.get("header").get(0);
        String problem = formParams.get("problem").get(0);
//        tasks.getInstance().add(new Task("H1", "P1"));
        Task newTask = new Task(header, problem);
        if(tasks.getInstance().contain(newTask))
            return new StringJson("ERROR. Task '" + header + "' has been created.");
        tasks.getInstance().add(newTask);
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
}