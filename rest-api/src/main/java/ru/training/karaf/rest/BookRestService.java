package ru.training.karaf.rest;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import ru.training.karaf.rest.dto.BookDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;

@Path("book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookRestService {

    @GET
    List<BookDTO> getAll(
            @QueryParam("title")@DefaultValue("%") String title, @QueryParam("genre") String genre, @QueryParam("author") String surname,
            @QueryParam("sz") int limit,
            @QueryParam("pg") int offset,@QueryParam("sortBy") String sortBy,@QueryParam("sortOrder") String order
    );

    @GET
    @Path("{name}")
    List<BookDTO> getAll(@PathParam("name") String name, @QueryParam("sz") int limit, @QueryParam("pg") int offset);

    @POST
    @Path("/create")
    void create(BookDTO book);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/image/adding")
    void addCover(@Multipart("image") InputStream stream, @QueryParam("id") Long id);

    @GET
    @Path("/image")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    byte[] showCover(@QueryParam("id") Long id);

    @PUT
    @Path("/update/{id}")
    void update(@PathParam("id") Long id, BookDTO book);

    @GET
    @Path("{id}")
    BookDTO get(@PathParam("id") Long id);

    @DELETE
    @Path("/delete/{id}")
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
    @Path("/title")
    List<BookDTO> searchByTitle(@QueryParam("title") String title, @QueryParam("sz") int limit, @QueryParam("pg") int offset);

    @GET
    @Path("/author")
    List<BookDTO> searchByAuthor(
            @QueryParam("name") String title, @QueryParam("surname") String surname, @QueryParam("sz") int limit,
            @QueryParam("pg") int offset, @QueryParam("sort") String sort
    );
}
