package ru.training.karaf.repo;

import ru.training.karaf.model.Genre;
import ru.training.karaf.model.Role;

import java.util.List;
import java.util.Optional;

public interface GenreRepo {

    List<? extends Genre> getAll();

    List<? extends Genre> getAll(String name, int limit, int offset,String sortBy, String order);

    void create(Genre genre);

    void update(Long id, Genre genre);

    Optional<? extends Genre> get(Long id);

    Optional<? extends Genre> get(String name);

    List<Long> showWithGenre(String name);

    void delete(Long id);
}
