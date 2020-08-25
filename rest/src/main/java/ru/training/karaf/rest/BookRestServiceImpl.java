package ru.training.karaf.rest;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;
import ru.training.karaf.repo.AuthorRepo;
import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.repo.GenreRepo;
import ru.training.karaf.repo.ReviewRepo;
import ru.training.karaf.rest.dto.AuthorDTO;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.GenreDTO;
import ru.training.karaf.rest.dto.ReviewDTO;
import ru.training.karaf.rest.exception.NoPermissionsException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BookRestServiceImpl implements BookRestService {
    private BookRepo repo;
    private AuthorRepo authorRepo;
    private ReviewRepo reviewRepo;
    private GenreRepo genreRepo;

    public BookRestServiceImpl(BookRepo repo, AuthorRepo authorRepo, ReviewRepo reviewRepo, GenreRepo genreRepo) {
        this.repo = repo;
        this.authorRepo = authorRepo;
        this.reviewRepo = reviewRepo;
        this.genreRepo = genreRepo;
    }

    @Override
    public List<BookDTO> getAll() {
        List<BookDTO> result = repo.getAll().stream().map(b -> new BookDTO(b)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<BookDTO> getAll(String name, int sz, int pg) {
        int offset = 0;
        if (pg > 0 & sz >= 0) {
            offset = sz * (pg - 1);
        }
        List<BookDTO> result = repo.searchByGenre(name, sz, offset).stream().map(b -> new BookDTO(b)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void create(BookDTO book) {
        if (ServiceUtils.isAdmin()) {
            List<AuthorDTO> list = new ArrayList<>();
            for (Author author : book.getAuthor()) {
                list.add(new AuthorDTO(author.getName(), author.getLastName(), authorRepo.get(author.getName(), author.getLastName()).get().getId()));
            }
            book.setAuthor(list);
            book.setGenre(new GenreDTO(book.getGenre().getName(), genreRepo.get(book.getGenre().getName()).get().getId()));
            repo.create(book);
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public void addCover(InputStream stream, Long id) {
        if (ServiceUtils.isAdmin()) {
            repo.addImage(stream, id);
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public byte[] showCover(Long id) {
        return repo.getImage(repo.get(id).get());
    }

    @Override
    public void update(Long id, BookDTO book) {
        if (ServiceUtils.isAdmin()) {
            repo.update(id, book);
        } else {
            throw new NoPermissionsException(ServiceUtils.updateMessage());
        }
    }

    @Override
    public BookDTO get(Long id) {
        return repo.get(id).map(u -> new BookDTO(u))
                .orElseThrow(() -> new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.APPLICATION_JSON_TYPE).entity("Book not found").build()));
    }

    @Override
    public void delete(Long id) {
        if (ServiceUtils.isAdmin()) {
            repo.delete(id);
        } else {
            throw new NoPermissionsException(ServiceUtils.deleteMessage());
        }
    }

    @Override
    public void addAuthor(Long id, Long authorId) {
        if (ServiceUtils.isAdmin()) {
            repo.addAuthor(id, authorRepo.get(authorId).get());
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
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
    public List<BookDTO> searchByTitle(String title, int sz, int pg) {
        int offset = 0;
        if (pg > 0 & sz >= 0) {
            offset = sz * (pg - 1);
        }
        if (pg == 0 & sz == 0) {
            offset = 1;
            sz = 20;
        }
        List<BookDTO> result = repo.searchByTitle(title, sz, offset).stream().map(b -> new BookDTO(b)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<BookDTO> searchByAuthor(String name, String surname, int sz, int pg, String sort) {
        int offset = pg > 0 & sz >= 0 ? sz * (pg - 1) : 0;
        List<BookDTO> result = repo.searchByAuthor(name, surname, sz, offset, sort).stream().map(b -> new BookDTO(b)).collect(Collectors.toList());
        return result;
    }
}
