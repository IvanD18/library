package ru.training.karaf.rest;

import ru.training.karaf.repo.UserRepo;

public  class ServiceUtils {
    private static UserRepo repo;


    public ServiceUtils() {
    }

    public static UserRepo getRepo() {
        return repo;
    }

    public void setRepo(UserRepo repo) {
        ServiceUtils.repo = repo;
    }
}
