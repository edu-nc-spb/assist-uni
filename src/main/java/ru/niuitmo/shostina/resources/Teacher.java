package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.AssignedTaskService;
import ru.niuitmo.shostina.services.ServiceException;
import ru.niuitmo.shostina.services.TaskService;
import ru.niuitmo.shostina.services.UserService;
import ru.niuitmo.shostina.utils.ListOfData;
import ru.niuitmo.shostina.utils.Task;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/user/teacher")
@AuthNeeded
public class Teacher {

    @Context
    ContainerRequestContext requestContext;

    private final UserService userService = new UserService();
    private final TaskService taskService = new TaskService();
    private final AssignedTaskService assignedTaskService = new AssignedTaskService();

    private static final String TASKID = "id_task";

    @Path("/create-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@FormParam("header") String header,
                               @FormParam("problem") String problem) {
        try {
            taskService.addTask(header, problem);
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
            return Response.ok(new ListOfData(taskService.getAllTasks())).build();
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
            long id = Long.parseLong(requestContext.getHeaders().getFirst("id"));
            return Response.ok(new ListOfData(assignedTaskService.getMyTasks(id))).build();
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
            return Response.ok(new ListOfData(userService.getStudents())).build();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@FormParam(TASKID) long idTask) {
        try {
            Task res = taskService.getTask(idTask);
            System.out.println("GET DEADLINE: " + res.getDeadline());
            return Response.ok(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/add-student")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@FormParam(TASKID) long idTask,
                               @FormParam("id_student") int idStudent, @FormParam("deadline") int deadline) {
        try {
            System.out.println("NEW DEADLINE: " + deadline);
            long id = Long.parseLong(requestContext.getHeaders().getFirst("id"));
            taskService.assignTask(id, idStudent, idTask, deadline);
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
    public Response showAnswer(@FormParam(TASKID) long idTask) {
        try {
            String json = assignedTaskService.showAnswer(idTask);
            return Response.ok(json).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/change-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeTask(@FormParam(TASKID) long idTask,
                               @FormParam("newProblem") String newProblem) {
        try {
            taskService.changeTask(idTask, newProblem);
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
    public Response deleteTask(@FormParam(TASKID) long idTask) {
        try {
            System.out.println(idTask);
            taskService.deleteTask(idTask);
            String json = "OK. Task was deleted";
            return Response.ok(json).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }
}