package ru.training.karaf.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
@NamedQueries({
        @NamedQuery(name = BookDO.GET_ALL, query = "SELECT u FROM BookDO AS u"),
        @NamedQuery(name = BookDO.GET_BY_ID, query = "SELECT u FROM BookDO AS u WHERE u.id = :id"),
        @NamedQuery(name = BookDO.SHOW_COMMENTS, query = "SELECT r.comment FROM BookDO AS b join ReviewDO as r WHERE b.id = :id"),
        @NamedQuery(name = BookDO.AVERAGE_RATING, query = "SELECT AVG(r.rating) FROM BookDO AS b join ReviewDO as r WHERE b.id = :id"),
        @NamedQuery(name = BookDO.COUNT_BOOKS, query = "SELECT COUNT(b) FROM BookDO AS b")
})

@NamedNativeQueries(
        {
                @NamedNativeQuery(name = BookDO.SEARCH_BY_AUTHOR,
                        query = "SELECT b.* FROM book as b where b.id in (select bookdo_id from book_author where author_id in(select a.id from " +
                                "author " +
                                "  as a where a.author_name LIKE ?1 and a.author_surname " +
                                "  LIKE ?2 order by a.author_surname ASC))  limit ?3 offset ?4", resultClass = BookDO.class),

                @NamedNativeQuery(name = BookDO.SEARCH_BY_TITLE, query = "SELECT b.* FROM book AS b WHERE b.title LIKE ?1 limit ?2 offset " +
                        "?3", resultClass = BookDO.class),
                @NamedNativeQuery(name = BookDO.GET_AUTHORS, query = "select a.* from author as a where a.id in (select ba.author_id from " +
                        "book_author" +
                        " as ba  " +
                        " where ba.bookdo_id = ?)", resultClass = AuthorDO.class),
                @NamedNativeQuery(name = BookDO.SEARCH_BY_GENRE,
                        query = "SELECT b.* FROM book as b where b.genre_id in(select g.id from genre as g where g.genre_name LIKE ?1)  limit" +
                                " ?2 offset ?3", resultClass = BookDO.class)
        }
)
public class BookDO implements Book {

    public static final String GET_ALL = "Book.getAll";
    public static final String GET_BY_ID = "Book.getById";
    public static final String SHOW_COMMENTS = "Book.showComments";
    public static final String AVERAGE_RATING = "Book.averageRating";
    public static final String SEARCH_BY_TITLE = "Book.searchByTitle";
    public static final String SEARCH_BY_GENRE = "Book.searchByGenre";
    public static final String SEARCH_BY_AUTHOR = "Book.searchByAuthor";
    public static final String GET_AUTHORS = "Book.getAuthors";
    public static final String COUNT_BOOKS = "Book.countBooks";

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "oid")
    private Long cover;

    private String title;

    @ManyToOne
    private GenreDO genre;

    @ManyToMany
    private List<AuthorDO> author;

    private boolean availability;

    public Long getCover() {
        return cover;
    }

    public void setCover(Long cover) {
        this.cover = cover;
    }

    public BookDO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
    }

    public BookDO(String title, GenreDO genre, List<AuthorDO> author, boolean availability) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.availability = availability;
    }

    public BookDO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(GenreDO genre) {
        this.genre = genre;
    }

    public void setAuthor(List<AuthorDO> author) {
        this.author = author;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    //    public void setReview(List<ReviewDO> review) {
    //        this.review = review;
    //    }

    //    public void setUser(UserDO user) {
    //        this.user = user;
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
    public GenreDO getGenre() {
        return genre;
    }

    public List<AuthorDO> getAuthor() {
        return author;
    }

    @Override
    public boolean getAvailability() {
        return availability;
    }

    //    public List<ReviewDO> getReview() {
    //        return review;
    //    }
}
