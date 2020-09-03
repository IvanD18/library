package ru.training.karaf.repo;

import ru.training.karaf.model.Review;
import ru.training.karaf.model.User;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo {
    List<? extends Review> getAll();

    List<? extends Review> getAll(int rating, String title, String login, int limit, int offset,String mode);

    void create(Review Review);

    void update(Long id, Review review);

    Optional<? extends Review> get(Long id);

    void delete(Long id);
}
