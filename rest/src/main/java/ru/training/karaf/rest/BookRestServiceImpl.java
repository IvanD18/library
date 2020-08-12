package ru.training.karaf.rest;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;
import ru.training.karaf.repo.AuthorRepo;
import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.repo.ReviewRepo;
import ru.training.karaf.rest.dto.AuthorDTO;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.ReviewDTO;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<BookDTO> getAll() {
//        int i = 0;
//        long id = 0;
//        String name,lastName;
//        List<BookDTO> a = new ArrayList<>();
//        List<? extends Book> list = repo.getAll();
//
//        for (Book book : list) {
//            a.add(new BookDTO(book));
//            id = a.get(i).getId();
//            List<? extends Author> list2 = repo.getAuthors(id);
//            List<AuthorDTO> b = new ArrayList<>();
//            for (Author author : list2) {
//                name = author.getName();
//                lastName = author.getLastName();
//
//                b.add(new AuthorDTO(name, lastName, author.getId()));
//            }
//
//            a.get(i).setAuthor(b);
//            i++;
//        }
        List<BookDTO> result = repo.getAll().stream().map(b -> new BookDTO(b)).collect(Collectors.toList());
        return result;
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
