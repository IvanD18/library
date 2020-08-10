package ru.training.karaf.rest;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;
import ru.training.karaf.repo.AuthorRepo;
import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.repo.ReviewRepo;
import ru.training.karaf.rest.dto.AuthorDTO;
import ru.training.karaf.rest.dto.BookDTO;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class BookRestServiceImpl implements BookRestService {
    BookRepo repo;
    AuthorRepo authorRepo;
    ReviewRepo reviewRepo;

    public BookRestServiceImpl(BookRepo repo, AuthorRepo authorRepo, ReviewRepo reviewRepo) {
        this.repo = repo;
        this.authorRepo = authorRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    public List<Long> getAll() {
        List<BookDTO> a= new ArrayList<>();
        List<Long> list = repo.getAll();
        return list;
    }

    @Override
    public void create(BookDTO book) {

        List<AuthorDTO> list = new ArrayList<>();
        for (Author author : book.getAuthor()) {
            list.add(new AuthorDTO(author.getName(), author.getLastName(), authorRepo.get(author.getName(), author.getLastName()).get().getId()));
        }
        book.setAuthor(list);
        repo.create(book);
    }

    @Override
    public void update(Long id, BookDTO book) {

    }

    @Override
    public BookDTO get(Long id) {
        return repo.get(id).map(u -> new BookDTO(u))
                .orElseThrow(() -> new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.APPLICATION_JSON_TYPE).entity("Book not found").build()));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void addAuthor(Long id, Long authorId) {
        repo.addAuthor(id, authorRepo.get(authorId).get());
    }

    @Override
    public List<String> showComments(Long id) {
        return repo.showComments(id);
    }

    @Override
    public double averageRating(Long id) {
        return repo.averageRating(id);
    }

    @Override
    public BookDTO searchByTitle(String title) {

        return new BookDTO(repo.searchByTitle(title));
    }
}
