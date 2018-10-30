
package resources;

import services.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Path("/teacher/{id}")
public class Teacher {
    private TaskByStudents myTasks;
    int cur = 0;
    private ListOfTasks tasks;

    private ListOfStudent students;

    @Path("/get-all-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        ArrayList<String> t = tasks.getInstance().getHeader();
        return Response.ok(new ListOfHeader(t), MediaType.APPLICATION_JSON).build();
    }


    @Path("/get-students")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        return Response.ok(students.getInstance(), MediaType.APPLICATION_JSON).build();
    }

    @Path("/get-my-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyTasks() {
        ArrayList<String> res = new ArrayList<>();
        ListOfHeader json = new ListOfHeader(myTasks.getInstance().get());
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@FormParam("header") String header) throws IOException {
        System.out.println("get " + header);
        if (tasks.getInstance().contain(header)) {
            Task json =  tasks.getInstance().getTask(header);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("ERROR. Task '" + header + "' wasn't created.").build();
    }

    @Path("/add-student")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@PathParam("id") int id, @FormParam("header") String header,
                               @FormParam("id") int idStudent) {
        if (tasks.getInstance().contain(header)) {
            myTasks.getInstance().addStudent(new LocalTask(tasks.getInstance().getTask(header), idStudent));
            String json = ("OK. Task '" + header + "' was added for student '" + idStudent + "'.");
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).
                    entity("Error. You don't have task '" + header + "'.").build();
    }

    @Path("/show-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAnswer(@PathParam("id") int id, @FormParam("header") String header,
                               @FormParam("id") int idStudent) throws IOException {
        if (myTasks.getInstance().contain(idStudent, header)) {
            String json = "OK. Answer for task '" + header +
                    "' by student '" + idStudent + "' is: '"
                    + myTasks.getInstance().getTask(idStudent, header).getAnswer() + "'.";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).
                    entity("Error. You don't have task '" + header + "'.").build();
    }

    @Path("/create-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@FormParam("header") String header,
                               @FormParam("problem") String problem) throws IOException {
        Task newTask = new Task(header, problem);
        System.out.println("createTask");
        if (tasks.getInstance().contain(newTask))
            return Response.status(Response.Status.NOT_FOUND).
                    entity("ERROR. Task '" + header + "' has been created.").build();

        tasks.getInstance().add(newTask);
        String json = "OK. You create new task '" + header + "'.";
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/change-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeTask(@FormParam("header") String header,
                               @FormParam("newProblem") String newProblem) throws IOException {
        if (!tasks.getInstance().contain(header))
            return Response.status(Response.Status.NOT_FOUND).
                    entity("ERROR. Task '" + header + "' hasn't been created.").build();
        tasks.getInstance().getTask(header).setProblem(newProblem);
        String json ="OK. You changed task '" + header + "'.";
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @Path("/delete-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@FormParam("header") String header) throws IOException {
        if (!tasks.getInstance().contain(header))
            return Response.status(Response.Status.NOT_FOUND).
                    entity("ERROR. Task '" + header + "' hasn't been created.").build();
        tasks.getInstance().delete(header);
        String json = "OK. You deleted task '" + header + "'.";
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}