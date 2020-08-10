package ru.training.karaf.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
@NamedQueries({
        //@NamedQuery(name = BookDO.GET_ALL, query = "SELECT u FROM BookDO AS u"),
        @NamedQuery(name = BookDO.GET_BY_ID, query = "SELECT u FROM BookDO AS u WHERE u.id = :id"),
        @NamedQuery(name = BookDO.SHOW_COMMENTS, query = "SELECT r.comment FROM BookDO AS b join ReviewDO as r WHERE b.id = :id"),
        @NamedQuery(name = BookDO.AVERAGE_RATING, query = "SELECT AVG(r.rating) FROM BookDO AS b join ReviewDO as r WHERE b.id = :id"),
        @NamedQuery(name = BookDO.SEARCH_BY_TITLE, query = "SELECT b FROM BookDO AS b WHERE b.title = :title")
})
//@NamedNativeQueries(
//        {
//                @NamedNativeQuery(name = BookDO.GET_ALL, query = " SELECT title, genre, availability, author_surname, author_name " +
//                        " FROM book " +
//                        " INNER JOIN book_author on book.id = bookdo_id " +
//                        " INNER JOIN author on author.id = author_id ")
//        }
//)
@NamedNativeQueries(
        {
                @NamedNativeQuery(name = BookDO.GET_ALL, query = "select bookdo_id, author_id from book_author order by bookdo_id")
        }
)
public class BookDO implements Book {

    public static final String GET_ALL = "Book.getAll";
    public static final String GET_BY_ID = "Book.getById";
    public static final String SHOW_COMMENTS = "Book.showComments";
    public static final String AVERAGE_RATING = "Book.averageRating";
    public static final String SEARCH_BY_TITLE = "Book.searchByAuthor";

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String genre;

    @ManyToMany
    private List<AuthorDO> author;

    private boolean availability;

//    @OneToMany
//    private List<ReviewDO> review;

    public BookDO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
    }

    public BookDO(String title, String genre, List<AuthorDO> author, boolean availability) {
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

    public void setGenre(String genre) {
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
    public String getGenre() {
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
