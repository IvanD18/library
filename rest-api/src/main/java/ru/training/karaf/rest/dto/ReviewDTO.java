package ru.training.karaf.rest.dto;

import ru.training.karaf.model.Book;
import ru.training.karaf.model.Review;
import ru.training.karaf.model.User;

public class ReviewDTO implements Review {
    private long id;
    private int rating;
    private String comment;
    private UserDTO user;
    private BookDTO book;

    public ReviewDTO(Review review) {
        this.id=review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.book=new BookDTO(review.getBook());
        this.user=new UserDTO(review.getUser());
    }

    public ReviewDTO(int rating, String comment, UserDTO user, BookDTO book) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.book = book;
    }

    public ReviewDTO() {
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public String getSurname(){
        return getUser().getLastName();
    }

    @Override
    public UserDTO getUser() {
        return user;
    }

    @Override
    public Book getBook() {
        return book;
    }


    @Override
    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }
}
