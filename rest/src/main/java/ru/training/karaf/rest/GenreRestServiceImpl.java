package ru.training.karaf.rest;

import ru.training.karaf.repo.GenreRepo;
import ru.training.karaf.rest.dto.GenreDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.exception.NoPermissionsException;

import java.util.List;
import java.util.stream.Collectors;

public class GenreRestServiceImpl implements GenreRestService {
    GenreRepo repo;

    public GenreRestServiceImpl(GenreRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<GenreDTO> getAll(String name, int limit, int offset) {
        name = name == null ? "%" : name;
        limit = (limit == 0) ? 10:limit;
        offset = offset>0? limit * (offset - 1): 0;
        List<GenreDTO> result = repo.getAll(name, limit, offset).stream().map(g -> new GenreDTO(g)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void create(GenreDTO genre) {
        if (ServiceUtils.isAdmin()) {
            repo.create(genre);
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public void update(Long id, GenreDTO genre) {
        if (ServiceUtils.isAdmin()) {
            repo.update(id, genre);
        } else {
            throw new NoPermissionsException(ServiceUtils.updateMessage());
        }
    }

    @Override
    public GenreDTO get(Long id) {
        return new GenreDTO(repo.get(id).get());
    }

    @Override
    public GenreDTO getByName(String name) {
        return new GenreDTO(repo.get(name).get());
    }

    @Override
    public void delete(Long id) {

    }
}
