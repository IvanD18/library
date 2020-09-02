package ru.training.karaf.repo;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.User;

import java.util.List;
import java.util.Optional;

public interface AuthorRepo {
    List<? extends Author> getAll();

    List<? extends Author> getAll(String name, String lastName, int limit, int offset);

    void create(Author author);

    void update(Long id, Author author);



    Optional<? extends Author> get(Long id);


    Optional<? extends Author> get(String name, String surname);

    List<Long> getBooks(Long id);

    void delete(Long id);


}
