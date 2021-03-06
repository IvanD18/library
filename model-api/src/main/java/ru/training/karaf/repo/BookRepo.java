package ru.training.karaf.repo;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookRepo {

    void addImage(InputStream stream, Long id);

    byte[] getImage(Book book);

    List<? extends Author> getAuthors(Long id);

    List<? extends Book> getAll();

    List<? extends Book> getAll(String title, String genre,String surname, int limit, int offset, String sortBy, String order);

    void create(Book book);

    void update(Long id, Book book);

    Optional<? extends Book> get(Long id);

    void delete(Long id);

    void addAuthor(Long id, Author author);

    List<String> showComments(Long id);

    double averageRating(Long id);

    Optional<? extends Book> getByTitle(String title);

    List<? extends Book> searchByTitle(String title, int limit, int offset);

    List<? extends Book> searchByGenre(String name, int limit, int offset);

    List<? extends Book> searchByAuthor(String name,String lastName, int limit, int offset,String sort);
}
