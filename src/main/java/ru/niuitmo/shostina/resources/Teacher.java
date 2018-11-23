
package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.*;
import ru.niuitmo.shostina.services.dataSets.ParamsDataSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/teacher/{id}")
public class Teacher {
    private DBService service;

    @Path("/create-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@FormParam("header") String header,
                               @FormParam("problem") String problem) throws IOException {

        try {
            service.instance().addTask(header, problem);
            String json = "OK. You create new task '" + header + "'.";
            System.out.println(json);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e){
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
            return Response.ok(new ListOfData(t), MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e){
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
            return Response.ok(new ListOfData(t), MediaType.APPLICATION_JSON).build();
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
            return Response.ok(new ListOfData(service.instance().getStudents()),
                    MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/get-task")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@FormParam("task_id") long task_id) throws IOException {
        System.out.println("GET TASK: ");
        System.out.println(task_id);
        try {
            Task json =  service.instance().getTask(task_id);
            System.out.println("GET TASK: " + json.getHeader() + " " + json.getProblem());
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e){
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
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }


/*

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
*/
}