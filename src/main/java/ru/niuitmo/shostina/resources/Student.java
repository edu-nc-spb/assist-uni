package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/student/{id}")
public class Student {
    private DBService service;

    @Path("/get-my-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyTasks() {
        try {
            List<Data> t = service.instance().getMyTasks(2);
            System.out.println(t);
            return Response.ok(new ListOfData(t), MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e){
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@FormParam("task_id") long task_id) throws IOException {
        try {
            Task json =  service.instance().getTask(task_id);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/add-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAnswer(@PathParam("id")int id,
                                @FormParam("id_task") long idTask,
                                @FormParam("answer") String answer){
        try {
            service.instance().addAnswer(id, idTask, answer);
            String json =  "OK. You add answer for task.";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
