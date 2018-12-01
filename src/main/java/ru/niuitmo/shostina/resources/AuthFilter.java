package ru.niuitmo.shostina.resources;

import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@AuthNeeded
@Priority(Priorities.AUTHENTICATION)

public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = "No token";
        System.out.println("Filter " + authorizationHeader);
        try {
            token = authorizationHeader.substring("Bearer".length()).trim();
            Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
            System.out.println("#### valid token : " + token);
        } catch (Exception e) {
            System.out.println("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
