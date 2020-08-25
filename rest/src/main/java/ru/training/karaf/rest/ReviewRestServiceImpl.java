package ru.training.karaf.rest;

import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.repo.ReviewRepo;
import ru.training.karaf.repo.UserRepo;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.ReviewDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;
import ru.training.karaf.rest.exception.NoPermissionsException;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewRestServiceImpl implements ReviewRestService {
    ReviewRepo repo;
    UserRepo userRepo;
    BookRepo bookRepo;

    public ReviewRestServiceImpl(ReviewRepo repo, UserRepo userRepo, BookRepo bookRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<ReviewDTO> getAll() {
        List<ReviewDTO> result = repo.getAll().stream().map(r -> new ReviewDTO(r)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void create(ReviewDTO review) {
        if (ServiceUtils.isAdmin()) {
            review.setUser(new UserDTO(userRepo.get(review.getUser().getId()).get()));
            review.setBook(new BookDTO(bookRepo.get(review.getBook().getId()).get()));
            repo.create(review);
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public void update(Long id, ReviewDTO review) {
        if (ServiceUtils.isAdmin()) {
            repo.update(id, review);
        } else {
            throw new NoPermissionsException(ServiceUtils.updateMessage());
        }
    }

    @Override
    public ReviewDTO get(Long id) {
        return new ReviewDTO(repo.get(id).get());
    }

    @Override
    public void delete(Long id) {
        if (ServiceUtils.isAdmin()) {
            repo.delete(id);
        } else {
            throw new NoPermissionsException(ServiceUtils.deleteMessage());
        }
    }
}
