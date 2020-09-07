package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import org.postgresql.PGConnection;
import org.postgresql.core.BaseConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import ru.training.karaf.model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class BookRepoImpl implements BookRepo {
    private JpaTemplate template;
    private DataSource datasource;

    static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "admin";

    public BookRepoImpl(JpaTemplate template, DataSource datasource) {
        this.template = template;
        this.datasource = datasource;
    }

    public void addReview(Long id, ReviewDO review) {
        template.tx(em -> {
            getById(id, em).ifPresent(bookToAdd -> {
                em.merge(bookToAdd);
            });
        });
    }

    @Override
    public void addImage(InputStream stream, Long id) {
        try (Connection conn = datasource.getConnection()) {

            conn.setAutoCommit(false);
            LargeObjectManager manager = conn.unwrap(PGConnection.class).getLargeObjectAPI();
            long oid = manager.createLO();
            LargeObject object = manager.open(oid);
            byte[] res = new byte[62914560];
            stream.read(res);
            object.write(res);
            object.close();
            conn.commit();
            template.tx(em -> {

                getById(id, em).ifPresent(bookToUpdate -> {
                    bookToUpdate.setCover(oid);
                    em.merge(bookToUpdate);
                });
            });
        } catch (SQLException | IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public byte[] getImage(Book book) {
        try (Connection conn = datasource.getConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager manager = conn.unwrap(PGConnection.class).getLargeObjectAPI();
            long oid = book.getCover();
            byte[] result = new byte[62914560];
            LargeObject lob = manager.open(oid, manager.READ);
            lob.getInputStream().read(result);
            return result;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            byte[] err = {0};
            return err;
        }
    }

    @Override
    public List<AuthorDO> getAuthors(Long id) {
        return template.txExpr(em -> getAuthors(id, em));
    }

    public List<AuthorDO> getAuthors(Long id, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.GET_AUTHORS).setParameter(1, id).getResultList();
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
        bookToCreate.setGenre(new GenreDO(book.getGenre()));
        bookToCreate.setAvailability(book.getAvailability());
        List<AuthorDO> list = new ArrayList<>();
        for (Author author : book.getAuthor()) {
            list.add(new AuthorDO(author));
        }
        bookToCreate.setGenre(new GenreDO(book.getGenre()));
        bookToCreate.setAuthor(list);
        template.tx(em -> em.persist(bookToCreate));
    }

    @Override
    public void update(Long id, Book book) {
        template.tx(em -> {
            getById(id, em).ifPresent(bookToUpdate -> {
                bookToUpdate.setTitle(book.getTitle());
                bookToUpdate.setGenre(new GenreDO(book.getGenre()));
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
    public Optional<? extends Book> getByTitle(String title) {
        return  Optional.of(template.txExpr(em -> searchByTitle(title , 1, 0, em)).get(0));
    }

    @Override
    public List<BookDO> searchByTitle(String title, int limit, int offset) {
        return template.txExpr(em -> searchByTitle("%" + title + "%", limit, offset, em));
    }

    private List<BookDO> searchByTitle(String title, int limit, int offset, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.SEARCH_BY_TITLE).setParameter(1, title).setParameter(2, limit).setParameter(
                    3, offset).getResultList();
        } catch (NoResultException e) {
            throw e;
        }
    }

    @Override
    public List<BookDO> searchByGenre(String name, int limit, int offset) {
        return template.txExpr(em -> searchByGenre(name, limit, offset, em));
    }

    @Override
    public List<? extends Book> searchByAuthor(String name, String lastName, int limit, int offset, String sort) {
        return template.txExpr(em -> searchByAuthor(name, lastName, limit, offset, sort, em));
    }

    private List<BookDO> searchByAuthor(String name, String lastName, int limit, int offset, String sort, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.SEARCH_BY_AUTHOR).setParameter(1, name).setParameter(2, lastName).setParameter(
                    3, limit).setParameter(4,offset).getResultList();
        } catch (NoResultException e) {
            throw e;
        }
    }

    private List<BookDO> searchByGenre(String name, int limit, int offset, EntityManager em) {
        try {
            return em.createNamedQuery(BookDO.SEARCH_BY_GENRE).setParameter(1, name).setParameter(2, limit).setParameter(
                    3, offset).getResultList();
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
