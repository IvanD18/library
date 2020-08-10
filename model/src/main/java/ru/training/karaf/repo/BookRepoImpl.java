package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

public class BookRepoImpl implements BookRepo {
    private JpaTemplate template;

    public BookRepoImpl(JpaTemplate template) {
        this.template = template;
    }

    //    public void addAuthor(Long id, AuthorDO author) {
    //        template.tx(em -> {
    //            getById(id, em).ifPresent(bookToAdd -> {
    //                List<AuthorDO> newList = bookToAdd.getAuthor();
    //                newList.add(author);
    //                bookToAdd.setAuthor(newList);
    //                em.merge(bookToAdd);
    //            });
    //        });
    //    }
    public void addReview(Long id, ReviewDO review) {
        template.tx(em -> {
            getById(id, em).ifPresent(bookToAdd -> {
                em.merge(bookToAdd);
            });
        });
    }

    @Override
    public List<AuthorDO> getAuthors(Long id) {
        return template.txExpr(em -> getAuthors(id, em));
    }

    public List<AuthorDO> getAuthors(Long id, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.GET_AUTHORS).setParameter(1,id).getResultList();
        } catch (NoResultException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<BookDO> getAll() {
        return template.txExpr(em -> em.createNamedQuery(BookDO.GET_ALL, BookDO.class).getResultList());
    }

    @Override
    public void create(Book book) {
        BookDO bookToCreate = new BookDO();
        bookToCreate.setTitle(book.getTitle());
        bookToCreate.setGenre(book.getGenre());
        bookToCreate.setAvailability(book.getAvailability());
        List<AuthorDO> list = new ArrayList<>();
        for (Author author : book.getAuthor()) {
            list.add(new AuthorDO(author));
        }
        bookToCreate.setAuthor(list);
        template.tx(em -> em.persist(bookToCreate));
    }

    @Override
    public void update(Long id, Book book) {
        template.tx(em -> {
            getById(id, em).ifPresent(bookToUpdate -> {
                bookToUpdate.setTitle(book.getTitle());
                bookToUpdate.setGenre(book.getGenre());
                bookToUpdate.setAvailability(book.getAvailability());

                em.merge(bookToUpdate);
            });
        });
    }

    @Override
    public Optional<? extends Book> get(Long id) {
        return template.txExpr(em -> getById(id, em));
    }

    public Optional<BookDO> getById(Long id, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(BookDO.GET_BY_ID, BookDO.class).setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        template.tx(em -> getById(id, em).ifPresent(em::remove));
    }

    @Override
    public void addAuthor(Long id, Author author) {
        template.tx(em -> {
            getById(id, em).ifPresent(userToUpdate -> {
                userToUpdate.getAuthor().add(new AuthorDO(author));
                em.merge(userToUpdate);
            });
        });
    }

    @Override
    public List<String> showComments(Long id) {
        return template.txExpr(em -> getComments(id, em));
    }

    @Override
    public double averageRating(Long id) {
        return template.txExpr(em -> averageRating(id, em));
    }

    @Override
    public BookDO searchByTitle(String title) {
        return template.txExpr(em -> searchByTitle(title, em));
    }

    private BookDO searchByTitle(String title, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.SEARCH_BY_TITLE, BookDO.class).setParameter("title", title).getSingleResult();
        } catch (NoResultException e) {
            throw e;
        }
    }

    private double averageRating(Long id, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.AVERAGE_RATING, Double.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    private List<String> getComments(Long id, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.SHOW_COMMENTS, String.class).setParameter("id", id).getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
