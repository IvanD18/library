package ru.training.karaf.rest;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;
import ru.training.karaf.repo.AuthorRepo;
import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.repo.GenreRepo;
import ru.training.karaf.repo.ReviewRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookRestServiceImplTest extends TestCase {
    @Test
    public void showCommentsEmptyList() {
        long id=6;
        List<String> list= new ArrayList<>();
        BookRepo repo = Mockito.mock(BookRepo.class);
        AuthorRepo authorRepo = Mockito.mock(AuthorRepo.class);
        ReviewRepo reviewRepo = Mockito.mock(ReviewRepo.class);
        GenreRepo genreRepo = Mockito.mock(GenreRepo.class);
        Mockito.doReturn(list).when(repo).showComments(id);
        BookRestService rest = new BookRestServiceImpl(repo, authorRepo, reviewRepo,genreRepo);
        List<String> a=rest.showComments(id);
        assertTrue(a.isEmpty());
    }

    @Test
    public void showCommentsNotEmptyList() {
        long id=7;
        List<String> list= new ArrayList<>();
        list.add("Good book");
        BookRepo repo = Mockito.mock(BookRepo.class);
        AuthorRepo authorRepo = Mockito.mock(AuthorRepo.class);
        ReviewRepo reviewRepo = Mockito.mock(ReviewRepo.class);
        GenreRepo genreRepo = Mockito.mock(GenreRepo.class);
        Mockito.doReturn(list).when(repo).showComments(id);
        BookRestService rest = new BookRestServiceImpl(repo, authorRepo, reviewRepo,genreRepo);
        List<String> a=rest.showComments(id);
        assertFalse(a.isEmpty());
    }

    @Test
    public void averageRatingCorrect() {
        double rating = 3.5;
        long id=7;
        BookRepo repo = Mockito.mock(BookRepo.class);
        AuthorRepo authorRepo = Mockito.mock(AuthorRepo.class);
        ReviewRepo reviewRepo = Mockito.mock(ReviewRepo.class);
        GenreRepo genreRepo = Mockito.mock(GenreRepo.class);
        Mockito.doReturn(rating).when(repo).averageRating(id);
        BookRestService rest = new BookRestServiceImpl(repo, authorRepo, reviewRepo,genreRepo);
        double ans=rest.averageRating(id);
        assertTrue(ans==rating);
    }
}
