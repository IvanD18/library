package ru.training.karaf.model;

import java.util.List;

public interface Book {


    Long getCover();

    Long getId();

    String getTitle();

    Genre getGenre();

    List<? extends Author> getAuthor();

    boolean getAvailability();


}
