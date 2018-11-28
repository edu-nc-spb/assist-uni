package ru.niuitmo.shostina.resources;


import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth")
public class Authorisation {

    //@Inject
    //private KeyGenerator keyGenerator;

    @POST
    @Path("/teacher")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateTeacher(@FormParam("login") String login,
                                        @FormParam("password") String password) {
        System.out.println("!!!AUTH: " + login + " " + password);
        try {

            // Authenticate the user using the credentials provided
            //authenticate(login, password);
            if (login.equals("teacher1") && password.equals("t1")) {
                String token = issueToken("1");
                System.out.println("OK: " + token);
                return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
                //return Response.ok("Bearer " + token, MediaType.APPLICATION_JSON).build();
            }
            System.out.println("No auth");
            throw new NotAuthorizedException("No auth");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/student")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response authenticateStudent(@FormParam("login") String login,
                                        @FormParam("password") String password) {

        try {
            // Authenticate the user using the credentials provided
            //authenticate(login, password);
            if (login.equals("student1") && password.equals("s1")) {
                String token = issueToken("2");
                return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
            } else {
                throw new NotAuthorizedException("No auth");
            }

        } catch (Exception e) {
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
