package ru.niuitmo.shostina.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ru.niuitmo.shostina.utils.User;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth")
public class Authorisation {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateTeacher(@FormParam("login") String login,
                                        @FormParam("password") String password) {
        try {
            if (login.equals("teacher1") && password.equals("t1")) {
                String token = issueToken("1");
                System.out.println("OK: " + token);
                return Response.ok(new User("Bearer " + token, 1)).build();
            } else if (login.equals("student1") && password.equals("s1")){
                String token = issueToken("2");
                System.out.println("OK: " + token);
                return Response.ok(new User("Bearer " + token, 2)).build();
            } else {
                throw new NotAuthorizedException("No auth");
            }
        } catch (Exception e) {
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
