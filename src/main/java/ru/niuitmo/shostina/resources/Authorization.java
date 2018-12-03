package ru.niuitmo.shostina.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ru.niuitmo.shostina.services.DBService;
import ru.niuitmo.shostina.utils.Data;
import ru.niuitmo.shostina.utils.User;

import java.util.List;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth")
public class Authorization {

    private DBService service;
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateTeacher(@FormParam("login") String login,
                                        @FormParam("password") String password) {
        try {
            List<Data> a = service.instance().getStudents();
            for(Data d : a) {
                System.out.println(d.getData() + " " + d.getId());
            }
            System.out.println(login + " " + password);
            User user = service.instance().checkUser(login, password);
            System.out.println("OK " + user.getToken());
            return Response.ok(new User("Bearer " + issueToken(user.getToken()), user.getRole())).build();
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
