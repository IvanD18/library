package ru.training.karaf.model;

import java.util.List;

public interface Review {
    Long getId();

    int getRating();

    String getSurname();

    User getUser();

    Book getBook();

    String getComment();
}
