package ru.training.karaf.rest;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ru.training.karaf.rest.dto.UserDTO;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserRestService {

    @GET
    List<UserDTO> getAll();

    @POST
    void create(UserDTO user);

    @PUT
    @Path("{login}")
    void update(@PathParam("login") String login, UserDTO user);

    @GET
    @Path("{login}")
    UserDTO get(@PathParam("login") String login);

    @DELETE
    @Path("{login}")
    void delete(@PathParam("login") String login);
}
