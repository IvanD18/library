package ru.training.karaf.repo;

public interface CountRepo {


    Long getCountOfReviews(int rating);


    Long getCountOfRoles();


    Long getCountOfBooks();

    Long getCountOfBooksForUser(Long user_id);

    Long getCountOfAvailableBooks();

    Long getCountOfNotAvailableBooks();

    Long getCountOfUsers(int age);


    Long getCountOfAuthors();
}
