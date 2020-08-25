package ru.training.karaf.rest.exceptionMapper;

import ru.training.karaf.rest.exception.NoPermissionsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NoPermissionsExceptionMapper implements ExceptionMapper<NoPermissionsException> {
    @Override
    public Response toResponse(NoPermissionsException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
    }
}
