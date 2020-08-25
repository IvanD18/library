package ru.training.karaf.rest;

import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("role")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RoleRestService {
    @GET
    List<RoleDTO> getAll();

    @POST
    @Path("/create")
    void create(RoleDTO role);

    @PUT
    @Path("/update/{id}")
    void update(@PathParam("id") Long id, RoleDTO role);

    @GET
    @Path("{id}")
    RoleDTO get(@PathParam("id") Long id);

    @GET
    @Path("{name}")
    RoleDTO getByName(@PathParam("name") String name);

    @DELETE
    @Path("/delete/{id}")
    void delete(@PathParam("id") Long id);

    @GET
    @Path("/users/{name}")
    List<UserDTO> showWithType(@PathParam("name") String name);
}
