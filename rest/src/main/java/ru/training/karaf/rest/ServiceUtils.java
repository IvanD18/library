package ru.training.karaf.rest;

import org.apache.shiro.SecurityUtils;
import ru.training.karaf.repo.UserRepo;

public class ServiceUtils {
    private static UserRepo repo;

    public ServiceUtils() {
    }

    public static UserRepo getRepo() {
        return repo;
    }

    public void setRepo(UserRepo repo) {
        ServiceUtils.repo = repo;
    }

    public static String getRole() {
        return repo.get(SecurityUtils.getSubject().getPrincipal().toString()).get().getRole().getName();
    }

    public static String getLogin() {
        return repo.get(SecurityUtils.getSubject().getPrincipal().toString()).get().getLogin();
    }
    public static Long getId() {
        return repo.get(SecurityUtils.getSubject().getPrincipal().toString()).get().getId();
    }

    public static String getFirstName() {
        return repo.get(SecurityUtils.getSubject().getPrincipal().toString()).get().getFirstName();
    }

    public static boolean isAdmin() {
        return getRole() == "admin" ? true : false;
    }

    public static boolean isUser() {
        return getRole() == "user" ? true : false;
    }
}
