package ru.training.karaf.rest;

import ru.training.karaf.rest.dto.AuthorDTO;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.UserDTO;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthorRestService {

    @GET
    List<AuthorDTO> getAll();

    @POST
    void create(AuthorDTO author);

    @PUT
    @Path("{id}")
    void update(@PathParam("id") Long id, AuthorDTO author);

    @GET
    @Path("{id}")
    AuthorDTO get(@PathParam("id") Long id);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);

    @GET
    @Path("{surname}/{name}")
    AuthorDTO get(@PathParam("name") String name,@PathParam("surname") String surname);

    @GET
    @Path("/books/{id}")
    List<BookDTO> showWithType(@PathParam("id") Long id);

}
