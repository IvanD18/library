package ru.training.karaf.rest;

import ru.training.karaf.repo.AuthorRepo;
import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.rest.dto.AuthorDTO;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorRestServiceImpl implements AuthorRestService {
    AuthorRepo repo;
    BookRepo bookRepo;

    public AuthorRestServiceImpl(AuthorRepo repo, BookRepo bookRepo) {
        this.repo = repo;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<AuthorDTO> getAll() {
        List<AuthorDTO> result = repo.getAll().stream().map(a -> new AuthorDTO(a)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void create(AuthorDTO author) {
        repo.create(author);
    }

    @Override
    public void update(Long id, AuthorDTO author) {

    }

    @Override
    public AuthorDTO get(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public AuthorDTO get(String name, String surname) {
        return repo.get(name, surname).map(u -> new AuthorDTO(u))
                .orElseThrow(() -> new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.APPLICATION_JSON_TYPE).entity("Author not found").build()));
    }

    @Override
    public List<BookDTO> showWithType(Long id) {
        List<Long> list = repo.getBooks(id);
        List<BookDTO> resultList = new ArrayList<>();
        for (Long aLong : list) {
            resultList.add(new BookDTO(bookRepo.get(aLong).get()));
        }
        return resultList;
    }
}
