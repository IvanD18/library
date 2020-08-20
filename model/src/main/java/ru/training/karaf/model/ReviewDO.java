package ru.training.karaf.model;

import javax.persistence.*;
import javax.xml.namespace.QName;

@Entity
@Table(name = "review")
@NamedQueries({
        @NamedQuery(name = ReviewDO.GET_ALL, query = "SELECT u FROM ReviewDO AS u"),
        @NamedQuery(name = ReviewDO.GET_BY_ID, query = "SELECT u FROM ReviewDO AS u WHERE u.id = :id"),
        @NamedQuery(name = ReviewDO.COUNT_REVIEWS, query = "SELECT COUNT(r) FROM ReviewDO AS r")
})
public class ReviewDO implements Review {
    public static final String GET_ALL = "Review.getAll";
    public static final String GET_BY_ID = "Review.getById";
    public static final String COUNT_REVIEWS = "Review.countReviews";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "book_rating")
    private int rating;

    @ManyToOne
    private UserDO user;

    @ManyToOne
    private BookDO book;

    private String comment;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public UserDO getUser() {
        return user;
    }

    @Override
    public BookDO getBook() {
        return book;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public void setBook(BookDO book) {
        this.book = book;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
