package ru.training.karaf.rest.dto;

import ru.training.karaf.model.Genre;

public class GenreDTO implements Genre {
    private String name;
    private Long id;

    public GenreDTO(Genre genre) {
        this.name = genre.getName();
        this.id = genre.getId();
    }

    public GenreDTO() {
    }

    public GenreDTO(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
