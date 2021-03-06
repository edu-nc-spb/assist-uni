package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.*;
import ru.niuitmo.shostina.models.ListOfData;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user/student")
@AuthNeeded
public class Student {

    @Context
    ContainerRequestContext requestContext;

    private final TaskService taskService = new TaskService();
    private final AssignedTaskService assignedTaskService = new AssignedTaskService();

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

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@FormParam("task_id") long idTask) {
        try {
            return Response.ok(taskService.getTask(idTask)).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/add-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAnswer(@FormParam("id_task") long idTask,
                              @FormParam("answer") String answer) {
        try {
            long id = Long.parseLong(requestContext.getHeaders().getFirst("id"));
            assignedTaskService.addAnswer(id, idTask, answer);
            return Response.ok("OK. You add answer for task.").build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
