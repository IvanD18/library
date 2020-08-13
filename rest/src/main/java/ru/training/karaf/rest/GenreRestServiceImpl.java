package ru.training.karaf.rest;

import ru.training.karaf.repo.GenreRepo;
import ru.training.karaf.rest.dto.GenreDTO;
import ru.training.karaf.rest.dto.RoleDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GenreRestServiceImpl implements GenreRestService {
    GenreRepo repo;

    public GenreRestServiceImpl(GenreRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<GenreDTO> getAll() {
        List<GenreDTO> result = repo.getAll().stream().map(g -> new GenreDTO(g)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void create(GenreDTO genre) {
        repo.create(genre);
    }

    @Override
    public void update(Long id, GenreDTO genre) {

    }

    @Override
    public GenreDTO get(Long id) {
        return null;
    }

    @Override
    public GenreDTO getByName(String name) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
