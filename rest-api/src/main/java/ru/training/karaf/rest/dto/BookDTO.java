package ru.training.karaf.rest.dto;

import ru.training.karaf.model.Author;
import ru.training.karaf.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDTO implements Book {
    private Long id;
    private String title;
    private GenreDTO genre;
    private List<AuthorDTO> author;
    private boolean availability;
    private Long cover;

    public BookDTO(Long id) {
        this.id = id;
    }

    public BookDTO() {
    }



    public BookDTO(Book book) {

        id = book.getId();
        this.title = book.getTitle();
        this.genre = new GenreDTO( book.getGenre());
        this.availability = book.getAvailability();
        this.cover = book.getCover();
        List<? extends Author> list = book.getAuthor();
        List<AuthorDTO> res = new ArrayList<>();
        for (Author author : list) {
            res.add(new AuthorDTO(author));
        }
        this.author = res;
    }

    //    public BookDTO( String title, String genre,  boolean availability, String lastName, String name
    //    ) {
    //        this.title = title;
    //        this.genre = genre;
    //        this.author.add (new AuthorDTO(name,lastName));
    //        this.availability = availability;
    //    }

    @Override
    public Long getCover() {
        return cover;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public GenreDTO getGenre() {
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
    public void setGenre(GenreDTO genre) {
        this.genre = genre;
    }
}
