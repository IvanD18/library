package ru.training.karaf.rest;




import ru.training.karaf.rest.dto.ReviewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("review")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ReviewRestService {
    @GET
    List<ReviewDTO> getAll();

    @POST
    void create(ReviewDTO review);

    @PUT
    @Path("{id}")
    void update(@PathParam("id") Long id, ReviewDTO review);

    @GET
    @Path("{id}")
    ReviewDTO get(@PathParam("id") Long id);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);
}
