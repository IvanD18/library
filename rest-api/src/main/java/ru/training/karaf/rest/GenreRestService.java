package ru.training.karaf.rest;

import ru.training.karaf.rest.dto.GenreDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("genre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GenreRestService {
    @GET
    List<GenreDTO> getAll(@QueryParam("genre") String name, @QueryParam("sz") int limit, @QueryParam("pg") int offset);

    @POST
    @Path("/create")
    void create(GenreDTO genre);

    @PUT
    @Path("/update/{id}")
    void update(@PathParam("id") Long id, GenreDTO genre);

    @GET
    @Path("{id}")
    GenreDTO get(@QueryParam("id") Long id);

    @GET
    @Path("/name")
    GenreDTO getByName(@QueryParam("name") String name);

    @DELETE
    @Path("/delete/{id}")
    void delete(@PathParam("id") Long id);

    //    @GET
    //    @Path("//{name}")
    //    List<GenreDTO> showWithType(@PathParam("name") String name);
}
