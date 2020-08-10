package ru.training.karaf.repo;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookRepo {

    List<Long> getAll();

    void create(Book book);

    void update(Long id, Book book);

    Optional<? extends Book> get(Long  id);

    void delete(Long id);

    void addAuthor(Long id, Author author);

    List<String> showComments(Long id);

    double averageRating(Long id);

    Book searchByTitle(String title);

}
