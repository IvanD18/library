package ru.training.karaf.rest;

import ru.training.karaf.repo.CountRepo;

public class CountRestServiceImpl implements CountRestService {
    CountRepo repo;

    public CountRestServiceImpl(CountRepo repo) {
        this.repo = repo;
    }

    @Override
    public Long getCountOfReviews() {
        return repo.getCountOfReviews();
    }

    @Override
    public Long getCountOfReviews(int rating) {
        return repo.getCountOfReviews();
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
    public Long getCountOfUsers() {
        return repo.getCountOfUsers();
    }

    @Override
    public Long getCountOfUsers(int age) {
        return repo.getCountOfUsers();
    }

    @Override
    public Long getCountOfAuthors() {
        return repo.getCountOfAuthors();
    }
}
