package ru.training.karaf.rest.dto;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;

import java.util.List;

public class AuthorDTO implements Author {

    private  Long id;
    private String name;
    private String lastName;

    public AuthorDTO() {
    }

    public AuthorDTO(Author author) {
        this.name = author.getName();
        this.lastName = author.getLastName();
    }

    public AuthorDTO( String name, String lastName,Long id) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public AuthorDTO(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }


}
