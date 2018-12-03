package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user/teacher/{id}")
@AuthNeeded
public class Teacher {
    private DBService service;

    @Path("/create-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@FormParam("header") String header,
                               @FormParam("problem") String problem) {

        try {
            service.instance().addTask(header, problem);
            String json = "OK. You create new task '" + header + "'.";
            System.out.println(json);
            return Response.ok(json).build();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/get-all-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        try {
            List<Data> t = service.instance().getAllTasks();
            System.out.println(t);
            return Response.ok(new ListOfData(t)).build();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/get-my-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyTasks() {
        try {
            List<Data> t = service.instance().getMyTasks(1);
            System.out.println(t);
            return Response.ok(new ListOfData(t)).build();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/get-students")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        try {
            return Response.ok(new ListOfData(service.instance().getStudents())).build();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@FormParam("task_id") long task_id) {
        try {
            Task json = service.instance().getTask(task_id);
            System.out.println("GET TASK: " + json.getHeader() + " " + json.getProblem());
            return Response.ok(json).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/add-student")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@PathParam("id") int id, @FormParam("id_task") long idTask,
                               @FormParam("id_student") int idStudent) {
        try {
            service.instance().assignTask(id, idStudent, idTask);
            String json = ("OK. Task was added for student '" + idStudent + "'.");
            return Response.ok(json).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/show-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAnswer(@PathParam("id") int id, @FormParam("id_task") long idTask,
                               @FormParam("id") int idStudent) {
        try {
            String json = service.instance().showAnswer(id, idStudent, idTask);
            return Response.ok(json).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/change-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeTask(@FormParam("id_task") long idTask,
                               @FormParam("newProblem") String newProblem) {
        try {
            service.instance().changeTask(idTask, newProblem);
            String json = "OK. Task was changed";
            return Response.ok(json).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/delete-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@FormParam("id_task") long idTask) {
        try {
            service.instance().deleteTask(idTask);
            String json = "OK. Task was deleted";
            return Response.ok(json).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }
}