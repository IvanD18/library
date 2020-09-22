package ru.training.karaf.model;

import javax.persistence.*;
import javax.xml.namespace.QName;

@Entity
@Table(name = "review")
@NamedQueries({
        @NamedQuery(name = ReviewDO.GET_ALL, query = "SELECT u FROM ReviewDO AS u"),
        @NamedQuery(name = ReviewDO.GET_BY_ID, query = "SELECT u FROM ReviewDO AS u WHERE u.id = :id"),
        @NamedQuery(name = ReviewDO.COUNT_REVIEWS, query = "SELECT COUNT(r) FROM ReviewDO AS r"),
        @NamedQuery(name = ReviewDO.COUNT_REVIEWS_WITH_RATING, query = "SELECT COUNT(r) FROM ReviewDO AS r WHERE r.rating > :rating")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = ReviewDO.SEARCH_WITH_MORE_RATING,
                query = "SELECT r.* FROM review AS r WHERE r.book_rating > #rating AND r.book_id IN (SELECT b.id FROM book AS b WHERE b.title LIKE " +
                        " #title) " +
                        "AND r.user_id IN (SELECT u.id FROM users AS u WHERE u.login LIKE #login) LIMIT #limit OFFSET #offset", resultClass =
                ReviewDO.class),
        @NamedNativeQuery(name = ReviewDO.SEARCH_WITH_LESS_RATING,
                query = "SELECT r.* FROM review AS r WHERE r.book_rating < #rating AND r.book_id IN (SELECT b.id FROM book AS b WHERE b.title LIKE " +
                        " #title) " +
                        "AND r.user_id IN (SELECT u.id FROM users AS u WHERE u.login LIKE #login) LIMIT #limit OFFSET #offset",
                resultClass = ReviewDO.class),
        @NamedNativeQuery(name = ReviewDO.SEARCH_WITH_EQUAL_RATING,
                query = "SELECT r.* FROM review AS r WHERE r.book_rating = #rating AND r.book_id IN (SELECT b.id FROM book AS b WHERE b.title LIKE " +
                        " #title) " +
                        "OR r.user_id IN (SELECT u.id FROM users AS u WHERE u.login LIKE #login) LIMIT #limit OFFSET #offset",
                resultClass = ReviewDO.class)
})

public class ReviewDO implements Review {
    public static final String GET_ALL = "Review.getAll";
    public static final String GET_BY_ID = "Review.getById";
    public static final String COUNT_REVIEWS = "Review.countReviews";
    public static final String COUNT_REVIEWS_WITH_RATING = "Review.countReviewsWithRating";
    public static final String SEARCH_WITH_MORE_RATING = "Review.searchMore";
    public static final String SEARCH_WITH_LESS_RATING = "Review.searchLess";
    public static final String SEARCH_WITH_EQUAL_RATING = "Review.searchEqual";

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
    public String getSurname() {
        return null;
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
