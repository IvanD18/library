package ru.training.karaf.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "author")
@NamedQueries({
        @NamedQuery(name = AuthorDO.GET_ALL, query = "SELECT u FROM AuthorDO AS u"),
        @NamedQuery(name = AuthorDO.GET_BY_ID, query = "SELECT u FROM AuthorDO AS u WHERE u.id = :id"),
        @NamedQuery(name = AuthorDO.GET_BY_NAME, query = "SELECT u FROM AuthorDO AS u WHERE u.name = :name and u.lastName = :lastName"),
        @NamedQuery(name = AuthorDO.GET_BOOKS, query = "SELECT b.id FROM AuthorDO AS a join BookDO as b WHERE a.id = :id")
})
public class AuthorDO implements Author {
    public static final String GET_ALL = "Author.getAll";
    public static final String GET_BY_ID = "Author.getById";
    public static final String GET_BY_NAME = "Author.getByName";
    public static final String GET_BOOKS = "Author.getBooks";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "author_name")
    private String name;

    @Column(name = "author_surname")
    private String lastName;

    public AuthorDO() {
    }

    public AuthorDO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.lastName = author.getLastName();
    }

    //@ManyToMany
    //private List<BookDO> book;

    public List<Book> getBook() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //    public void setBook(List<BookDO> book) {
    //        this.book = book;
    //    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }
}
