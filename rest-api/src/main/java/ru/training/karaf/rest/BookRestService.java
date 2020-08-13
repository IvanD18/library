package ru.training.karaf.rest;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import ru.training.karaf.rest.dto.BookDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Path("book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookRestService {

    @GET
    List<BookDTO> getAll();

    @POST
    void create(BookDTO book);

    @POST
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void addCover(@Multipart("image") InputStream stream, @QueryParam("id") Long id);

    @GET
    @Path("/image")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    byte[] showCover(@QueryParam("id") Long id);

    @PUT
    @Path("{id}")
    void update(@PathParam("id") Long id, BookDTO book);

    @GET
    @Path("{id}")
    BookDTO get(@PathParam("id") Long id);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);

    @GET
    @Path("/author/adding/{id}/{author_id}")
    void addAuthor(@PathParam("id") Long id, @PathParam("author_id") Long authorId);

    @GET
    @Path("/comments/{id}")
    List<String> showComments(@PathParam("id") Long id);

    @GET
    @Path("/rating/{id}")
    double averageRating(@PathParam("id") Long id);

    @GET
    @Path("/find/{title}")
    BookDTO searchByTitle(@PathParam("title") String title);
}
