package ru.training.karaf.rest.exceptionMapper;

import org.apache.shiro.authc.IncorrectCredentialsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class IncorrectCredentialsExceptionMapper implements ExceptionMapper<IncorrectCredentialsException> {
    @Override
    public Response toResponse(IncorrectCredentialsException e) {
        return Response.status(Response.Status.UNAUTHORIZED).entity("Incorrect password or login").build();
    }
}
