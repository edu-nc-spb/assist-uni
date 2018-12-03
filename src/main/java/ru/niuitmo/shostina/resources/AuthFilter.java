package ru.niuitmo.shostina.resources;

import io.jsonwebtoken.Jwts;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@AuthNeeded
@Priority(Priorities.AUTHENTICATION)

public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println(authorizationHeader);
        String token = "No token";
        System.out.println("Filter " + authorizationHeader);
        try {
            token = authorizationHeader.substring("Bearer".length()).trim();
            Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
            String id = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();
            System.out.println(id);
            requestContext.getHeaders().add(HttpHeaders.COOKIE, id);
            requestContext.getHeaders().putSingle("id", id);
            System.out.println("#### valid token : " + token);
        } catch (Exception e) {
            System.out.println("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
