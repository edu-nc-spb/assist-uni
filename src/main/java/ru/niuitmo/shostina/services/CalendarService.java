package ru.niuitmo.shostina.services;

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
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.niuitmo.shostina.resources.GoogleCalendar;
import ru.niuitmo.shostina.services.dao.ObjectsDAO;
import ru.niuitmo.shostina.services.datasets.ObjectsDataSet;
import ru.niuitmo.shostina.services.datasets.ParamsDataSet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CalendarService extends ServiceUtils {

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

    public com.google.api.services.calendar.Calendar calendarAuth(long userId)
            throws GeneralSecurityException, IOException {
        Session session = SESSIONFACTORY.openSession();
        ObjectsDataSet user = (new ObjectsDAO(session)).get(userId);
        List<ParamsDataSet> params = user.getParams();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        com.google.api.services.calendar.Calendar service =
                new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                        getCredentials(HTTP_TRANSPORT, "tokens/" + userId))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        boolean auth = false;
        for (ParamsDataSet param : params) {
            if (param.getAttr().equals(CALENDAR)) {
                auth = true;
            }
        }
        if (!auth) {
            Transaction transaction = session.beginTransaction();
            params.add(createParam(session, user, CALENDAR, CALENDAR_AUTH));
            session.update(user);
            transaction.commit();
        }
        session.close();
        return service;
    }

    public void addEvent(String task, long userId, Date deadline) throws GeneralSecurityException, IOException {
        boolean auth = false;
        Session session = SESSIONFACTORY.openSession();
        ObjectsDataSet user = (new ObjectsDAO(session)).get(userId);
        List<ParamsDataSet> params = user.getParams();
        for (ParamsDataSet param : params) {
            if (param.getAttr().equals(CALENDAR) && param.getTextValue().equals(CALENDAR_AUTH)) {
                auth = true;
            }
        }
        session.close();
        if (auth) {
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
    }
}
