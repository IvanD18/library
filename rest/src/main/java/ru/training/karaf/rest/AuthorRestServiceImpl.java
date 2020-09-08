package ru.training.karaf.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ru.training.karaf.repo.AuthorRepo;
import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.rest.dto.AuthorDTO;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;
import ru.training.karaf.rest.exception.NoPermissionsException;

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

//    @Override
//    public List<AuthorDTO> getAll() {
//
//        List<AuthorDTO> result = repo.getAll().stream().map(a -> new AuthorDTO(a)).collect(Collectors.toList());
//        return result;
//    }

    @Override
    public List<AuthorDTO> getAll(String name, String lastName, int limit, int offset, String sortBy,String order) {
        limit = (limit == 0) ? 10:limit;
        offset = offset>0? limit * (offset - 1): 0;
        name= name ==null? "%":name;
        lastName= lastName ==null? "%":lastName;
        List<AuthorDTO> result = repo.getAll(name, lastName, limit, offset, sortBy, order).stream().map(a -> new AuthorDTO(a)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void create(AuthorDTO author) {
        if (ServiceUtils.isAdmin()) {
            repo.create(author);
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public void update(Long id, AuthorDTO author) {
        if (ServiceUtils.isAdmin()) {
            repo.update(id, author);
        } else {
            throw new NoPermissionsException(ServiceUtils.updateMessage());
        }
    }

    @Override
    public AuthorDTO get(Long id) {
        return new AuthorDTO(repo.get(id).get());
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
