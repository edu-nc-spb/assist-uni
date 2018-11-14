package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.ListOfTasks;
import ru.niuitmo.shostina.services.StudentByTasks;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.NoSuchElementException;

@Path("/student/{id}")
public class Student {
    private StudentByTasks myTasks;
    private ListOfTasks tasks;

    @Path("/get-my-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyTasks(@PathParam("id") int id) {
        return Response.ok(new ListOfHeader(myTasks.getInstance().getHeader(id)),
                MediaType.APPLICATION_JSON).build();
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") int id, @FormParam("header") String header)
            throws NoSuchElementException {
        return Response.ok(myTasks.getInstance().getTask(id, header),
                MediaType.APPLICATION_JSON).build();
    }

    @Path("/add-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAnswer(@PathParam("id")int id,
                                @FormParam("header") String header,
                                @FormParam("answer") String answer) throws IOException {
        if(myTasks.getInstance().contain(id, header)) {
            myTasks.getInstance().getTask(id, header).setAnswer(answer);
            String resp = "OK. You add answer for task '" + header + "'.";
            return Response.ok(resp, MediaType.APPLICATION_JSON).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).
                    entity("ERROR. Task '" + header + "' wasn't created.").build();
    }
}
