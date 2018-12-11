package ru.niuitmo.shostina.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ru.niuitmo.shostina.services.ServiceException;
import ru.niuitmo.shostina.services.UserService;
import ru.niuitmo.shostina.utils.CalendarAuthorization;
import ru.niuitmo.shostina.utils.User;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth")
public class Authorization {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(@FormParam("login") String login,
                                        @FormParam("password") String password) throws Exception {
        try {
            User user = (new UserService()).getUser(login, password);
            CalendarAuthorization.calendarAuth(login);
            return Response.ok(new User("Bearer " + issueToken(user.getToken()), user.getRole())).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private String issueToken(String login) {
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
        return jwtToken;
    }
}
