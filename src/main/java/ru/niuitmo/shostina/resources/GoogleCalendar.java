package ru.niuitmo.shostina.resources;

import ru.niuitmo.shostina.services.CalendarService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Path("/user/calendar")
@AuthNeeded
public class GoogleCalendar {

    @Context
    ContainerRequestContext requestContext;

    private final CalendarService calendarService = new CalendarService();

    @Path("/auth")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth() {
        try {
            String id = (requestContext.getHeaders().getFirst("id"));
            calendarService.calendarAuth(Long.parseLong(id));
            String json = "OK. You authorize in Google Calendar.";
            return Response.ok(json).build();
        } catch (GeneralSecurityException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

}
