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
        @NamedQuery(name = BookDO.COUNT_BOOKS, query = "SELECT COUNT(b) FROM BookDO AS b"),
        @NamedQuery(name = BookDO.COUNT_AVAILABLE_BOOKS, query = "SELECT COUNT(b) FROM BookDO AS b where b.availability = 'true'"),
        @NamedQuery(name = BookDO.COUNT_NOT_AVAILABLE_BOOKS, query = "SELECT COUNT(b) FROM BookDO AS b where b.availability = 'false'")
})

@NamedNativeQueries(
        {
                @NamedNativeQuery(name = BookDO.SEARCH_BY_AUTHOR,
                        query = "SELECT b.* FROM book as b where b.id in (select bookdo_id from book_author where author_id in(select a.id from " +
                                "author " +
                                "  as a where a.author_name LIKE #name and a.author_surname " +
                                "  LIKE #surname order by a.author_surname ASC))  limit #limit offset #offset", resultClass = BookDO.class),

                @NamedNativeQuery(name = BookDO.SEARCH_BY_TITLE, query = "SELECT b.* FROM book AS b WHERE b.title LIKE #title limit #limit offset " +
                        "#offset", resultClass = BookDO.class),
                @NamedNativeQuery(name = BookDO.GET_AUTHORS, query = "select a.* from author as a where a.id in (select ba.author_id from " +
                        "book_author" +
                        " as ba  " +
                        " where ba.bookdo_id = #id)", resultClass = AuthorDO.class),
                @NamedNativeQuery(name = BookDO.SEARCH_BY_GENRE,
                        query = "SELECT b.* FROM book as b where b.genre_id in(select g.id from genre as g where g.genre_name LIKE #genre)  limit" +
                                " #limit offset #offset", resultClass = BookDO.class),
                @NamedNativeQuery(name = BookDO.SEARCH, query = BookDO.SEARCH_QUERY +
                        ""
                        + BookDO.LIMIT_OFFSET, resultClass = BookDO.class),
                @NamedNativeQuery(name = BookDO.SEARCH_TITLE_ASC, query = BookDO.SEARCH_QUERY +
                        " ORDER BY title ASC "
                        + BookDO.LIMIT_OFFSET, resultClass = BookDO.class),
                @NamedNativeQuery(name = BookDO.SEARCH_TITLE_DESC, query = BookDO.SEARCH_QUERY +
                        " ORDER BY title DESC "
                        + BookDO.LIMIT_OFFSET, resultClass = BookDO.class)
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
    public static final String COUNT_NOT_AVAILABLE_BOOKS = "Book.countAvailableBooks";
    public static final String COUNT_AVAILABLE_BOOKS = "Book.countNotAvailableBooks";
    public static final String SEARCH = "Book.search";
    public static final String SEARCH_TITLE_ASC = "Book.searchTitleAsc";
    public static final String SEARCH_TITLE_DESC = "Book.searchTitleDesc";

    public static final String SEARCH_QUERY = " SELECT b.* FROM book AS b WHERE b.title LIKE #title AND b.genre_id IN (SELECT id FROM " +
            "genre AS g WHERE genre_name LIKE #genre) AND b.id IN (SELECT bookdo_id FROM book_author WHERE author_id IN (SELECT id FROM " +
            "author WHERE author_surname LIKE #surname)) ";
    public static final String LIMIT_OFFSET = " LIMIT #limit OFFSET #offset ";

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


}
