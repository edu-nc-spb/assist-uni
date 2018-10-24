package services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Path("/student/{id}")
public class Student {
    private StudentByTasks myTasks;
    int cur= 0;
    private ListOfTasks tasks;

    @Path("/get-my-tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ListOfHeader getMyTasks(@PathParam("id") int id) {
        return new ListOfHeader(myTasks.getInstance().getHeader(id));
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public LocalTask getTask(@PathParam("id") int id,  MultivaluedMap<String, String> formParams) throws NoSuchElementException {
        String header = formParams.get("header").get(0);
        return myTasks.getInstance().getTask(id, header);
    }

    @Path("/add-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public StringJson addAnswer(@PathParam("id")int id, MultivaluedMap<String, String> formParams) throws IOException {
        String header = formParams.get("header").get(0);
        String answer = formParams.get("answer").get(0);
        if(myTasks.getInstance().contain(id, header)) {
            myTasks.getInstance().getTask(id, header).setAnswer(answer);
            return new StringJson("OK. You add answer for task '" + header + "'.");
        } else
            return new StringJson("Error. You don't have task '" + header + "'.");
    }

    @Path("/change-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public StringJson changeAnswer(@PathParam("id")int id, MultivaluedMap<String, String> formParams) throws IOException {
        return addAnswer(id, formParams);
    }

    @Path("/delete-answer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public StringJson deleteAnswer(@PathParam("id")int id, MultivaluedMap<String, String> formParams) throws IOException {
        String header = formParams.get("header").get(0);
        if(myTasks.getInstance().contain(id, header)) {
            myTasks.getInstance().getTask(id, header).deleteAnswer();
            return new StringJson("OK. You deleted your answer for task '" + header + "'.");
        } else
            return new StringJson("Error. You don't have task '" + header + "'.");
    }


}
