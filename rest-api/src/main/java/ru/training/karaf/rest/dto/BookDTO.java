package ru.training.karaf.rest.dto;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;

import java.util.List;
import java.util.Optional;

public class BookDTO implements Book {
    private Long id;
    private String title;
    private String genre;
    private List<AuthorDTO> author;
    private boolean availability;

    public BookDTO(Long id) {
        this.id = id;
    }

    public BookDTO() {
    }

    public BookDTO(Book book) {
        id=book.getId();
        this.title = book.getTitle();
        this.genre = book.getGenre();
        this.availability = book.getAvailability();
    }

//    public BookDTO( String title, String genre,  boolean availability, String lastName, String name
//    ) {
//        this.title = title;
//        this.genre = genre;
//        this.author.add (new AuthorDTO(name,lastName));
//        this.availability = availability;
//    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public List<AuthorDTO> getAuthor() {
        return author;
    }

    @Override
    public boolean getAvailability() {
        return availability;
    }

    public void setAuthor(List<AuthorDTO> author) {
        this.author = author;
    }
}
