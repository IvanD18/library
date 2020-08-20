package ru.training.karaf.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("count")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CountRestService {
    @GET
    @Path("/review")
    Long getCountOfReviews();

    @GET
    @Path("/review")
    Long getCountOfReviews(@QueryParam("rating")int rating);

    @GET
    @Path("/role")
    Long getCountOfRoles();

    @GET
    @Path("/book")
    Long getCountOfBooks();

    @GET
    @Path("/users")
    Long getCountOfUsers();

    @GET
    @Path("/users")
    Long getCountOfUsers(@QueryParam("age")int age);

    @GET
    @Path("/author")
    Long getCountOfAuthors();

}
