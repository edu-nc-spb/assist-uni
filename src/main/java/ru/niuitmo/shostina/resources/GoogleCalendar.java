package ru.niuitmo.shostina.resources;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Path("/user/calendar")
@AuthNeeded
public class GoogleCalendar {

    @Context
    ContainerRequestContext requestContext;

    private static final String APPLICATION_NAME = "Assist Uni";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String tokenDirectoryPath)
            throws IOException {
        InputStream in = GoogleCalendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokenDirectoryPath)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static com.google.api.services.calendar.Calendar calendarAuth(String id)
            throws GeneralSecurityException, IOException {
        System.out.print("calendar auth");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        com.google.api.services.calendar.Calendar service =
                new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                        getCredentials(HTTP_TRANSPORT, "tokens/" + id))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

    private static void addEvent(String task, String userId, Date deadline) throws GeneralSecurityException, IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Event event = new Event()
                .setSummary("Assist Uni: new Task")
                .setDescription("Вам назначили новое задание: " + task);

        EventDateTime start = new EventDateTime()
                .setDateTime(now)
                .setTimeZone("UTC+3:00");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(new DateTime(deadline))
                .setTimeZone("UTC+3:00");
        event.setEnd(end);
        String calendarId = "primary";
        com.google.api.services.calendar.Calendar service = calendarAuth(userId);
        service.events().insert(calendarId, event).execute();
    }

    @Path("/event")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEvent(@FormParam("header") String header, @FormParam("deadline") String deadline) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = formatter.parse(deadline);
            String id = (requestContext.getHeaders().getFirst("id"));
            GoogleCalendar.addEvent(header, id, date);
            String json = "OK. You add new event into google calendar '" + header + "'.";
            return Response.ok(json).build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        } catch (ParseException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        }
    }

    @Path("/auth")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth() {
        try {
            String id = (requestContext.getHeaders().getFirst("id"));
            calendarAuth(id);
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
