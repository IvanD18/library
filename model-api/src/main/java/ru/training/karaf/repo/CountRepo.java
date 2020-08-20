package ru.training.karaf.repo;

public interface CountRepo {

    Long getCountOfReviews();


    Long getCountOfReviews(int rating);


    Long getCountOfRoles();


    Long getCountOfBooks();


    Long getCountOfUsers();


    Long getCountOfUsers(int age);


    Long getCountOfAuthors();
}
