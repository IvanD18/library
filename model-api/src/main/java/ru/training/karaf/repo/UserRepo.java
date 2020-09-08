package ru.training.karaf.repo;

import java.util.List;
import java.util.Optional;

import ru.training.karaf.model.Book;
import ru.training.karaf.model.User;

public interface UserRepo {
    List<? extends User> getAll();

    void create(User user);

    void update(String login, User user);

    Optional<? extends User> get(String login);

    Optional<? extends User> get(Long id);

    void delete(String login);

    void addBook(Long id, Book book);

    List<Long> showBooks(Long id);

    Long showRole(Long id);

    void removeBook(Long id, Long bookId);

    List<? extends User> searchByAge(int age, String ratio, String role, int limit, int offset, String address);

    List<? extends User> searchByAddress(String address, int limit, int offset);
}
