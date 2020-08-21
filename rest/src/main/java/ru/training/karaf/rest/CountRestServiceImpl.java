package ru.training.karaf.rest;

import ru.training.karaf.repo.CountRepo;

public class CountRestServiceImpl implements CountRestService {
    CountRepo repo;

    public CountRestServiceImpl(CountRepo repo) {
        this.repo = repo;
    }



    @Override
    public Long getCountOfReviews(int rating) {
        return repo.getCountOfReviews(rating);
    }

    @Override
    public Long getCountOfRoles() {
        return repo.getCountOfRoles();
    }

    @Override
    public Long getCountOfBooks() {
        return repo.getCountOfBooks();
    }

    @Override
    public Long getCountOfAvailableBooks() {
        return repo.getCountOfAvailableBooks();
    }

    @Override
    public Long getCountOfNotAvailableBooks() {
        return repo.getCountOfNotAvailableBooks();
    }

    @Override
    public Long getCountOfBooks(Long id) {
        return repo.getCountOfBooksForUser(id);
    }

    @Override
    public Long getCountOfUsers(int age) {
        return repo.getCountOfUsers(age);
    }

    @Override
    public Long getCountOfAuthors() {
        return repo.getCountOfAuthors();
    }
}
