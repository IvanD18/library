package ru.training.karaf.rest;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.LoginfoDTO;
import ru.training.karaf.rest.dto.UserDTO;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserRestService {

    @GET
    @Path("/books/{id}")
    List<BookDTO> showBooks(@PathParam("id") Long id);

    @GET
    @Path("/taking/{id}/{book_id}")
    String takeBook(@PathParam("id") Long id, @PathParam("book_id") Long bookId);

    @GET
    List<UserDTO> getAll() throws Exception;

    @POST
    @Path("/registration")
    UserDTO create(UserDTO user);

    @PUT
    @Path("{login}")
    void update(@PathParam("login") String login, UserDTO user);

    @GET
    @Path("{login}")
    UserDTO get(@PathParam("login") String login);

    @DELETE
    @Path("{login}")
    void delete(@PathParam("login") String login);

    @GET
    @Path("/returning/{id}/{book_id}")
    void returnBook(@PathParam("id") Long id, @PathParam("book_id") Long bookId);

    @POST
    @Path("/login")
    public UserDTO login(LoginfoDTO loginfo);
}
