package ru.training.karaf.repo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.*;

public class UserRepoImpl implements UserRepo {
    private JpaTemplate template;

    public UserRepoImpl(JpaTemplate template) {
        this.template = template;
    }

    @Override
    public List<? extends User> getAll() {
        return template.txExpr(em -> em.createNamedQuery(UserDO.GET_ALL, UserDO.class).getResultList());
    }

    @Override
    public void create(User user) {
        UserDO userToCreate = new UserDO();
        userToCreate.setLogin(user.getLogin());
        userToCreate.setFirstName(user.getFirstName());
        userToCreate.setLastName(user.getLastName());
        userToCreate.setAddress(user.getAddress());
        userToCreate.setAge(user.getAge());
        userToCreate.setPassword(user.getPassword());
        userToCreate.setProperties(user.getProperties());
        userToCreate.setRole(new RoleDO(user.getRole()));
        template.tx(em -> em.persist(userToCreate));
    }

    @Override
    public void update(String login, User user) {
        template.tx(em -> {
            getByLogin(login, em).ifPresent(userToUpdate -> {
                userToUpdate.setLogin(user.getLogin());
                userToUpdate.setFirstName(user.getFirstName());
                userToUpdate.setLastName(user.getLastName());
                userToUpdate.setAddress(user.getAddress());
                userToUpdate.setAge(user.getAge());
                userToUpdate.setProperties(user.getProperties());
                userToUpdate.setRole(new RoleDO(user.getRole()));
                em.merge(userToUpdate);
            });
        });
    }

    @Override
    public Optional<? extends User> get(String login) {
        return template.txExpr(em -> getByLogin(login, em));
    }

    @Override
    public Optional<? extends User> get(Long id) {
        return template.txExpr(em -> getById(id, em));
    }

    @Override
    public void delete(String login) {
        template.tx(em -> getByLogin(login, em).ifPresent(em::remove));
    }

    @Override
    public void addBook(Long id, Book book) {

        template.tx(em -> {
            getById(id, em).ifPresent(userToUpdate -> {
                        userToUpdate.getBook().add(new BookDO(book));
                        em.merge(userToUpdate);
                    }

            );
        });
    }

    @Override
    public List<Long> showBooks(Long id) {
        return template.txExpr(em -> getBooks(id, em));
    }

    @Override
    public Long showRole(Long id) {
        return template.txExpr(em -> getRole(id, em));
    }

    @Override
    public void removeBook(Long id, Long bookId) {
        template.tx(em -> {
            removeBook(id, bookId, em);
        });
    }

    @Override
    public List<? extends User> searchByAge(int age, String ratio, int limit, int offset, String address) {
        return template.txExpr(em -> searchByAge(age, ratio, limit, offset, address, em));
    }

    public List<? extends User> searchByAge(int age, String ratio, int limit, int offset, String address, EntityManager em) {

        if (ratio.equals("less")) {
            try {
                return em.createNamedQuery(UserDO.SEARCH_WITH_AGE_LESS).setParameter(1, age).setParameter(2, address).setParameter(3, limit)
                        .setParameter(4, offset)
                        .getResultList();
            } catch (NoResultException e) {
                throw e;
            }
        }
        if (ratio.equals("equally")) {
            try {
                return em.createNamedQuery(UserDO.SEARCH_WITH_AGE).setParameter(1, age).setParameter(2, address).setParameter(3, limit).setParameter(
                        4, offset)
                        .getResultList();
            } catch (NoResultException e) {
                throw e;
            }
        } else {
            try {
                return em.createNamedQuery(UserDO.SEARCH_WITH_AGE_MORE).setParameter(1, age).setParameter(2, address).setParameter(3, limit)
                        .setParameter(4, offset)

                        .getResultList();
            } catch (NoResultException e) {
                throw e;
            }
        }
    }

    @Override
    public List<? extends User> searchByAddress(String address, int limit, int offset) {
        return template.txExpr(em -> searchByAddress("%" + address + "%", limit, offset, em));
    }

    public List<? extends User> searchByAddress(String address, int limit, int offset, EntityManager em) {
        try {
            return em.createNamedQuery(UserDO.SEARCH_BY_ADDRESS).setParameter(1, address).setParameter(2, limit).setParameter(3, offset)
                    .getResultList();
        } catch (NoResultException e) {
            throw e;
        }
    }

    private Optional<UserDO> getByLogin(String login, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(UserDO.GET_BY_LOGIN, UserDO.class).setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private void removeBook(Long id, Long bookId, EntityManager em) {
        try {
            em.createNamedQuery(UserDO.DELETE_BOOK).setParameter("id", id).setParameter("book_id", bookId).getFirstResult();
        } catch (NoResultException e) {
        }
    }

    private Optional<UserDO> getById(Long id, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(UserDO.GET_BY_ID, UserDO.class).setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private List<Long> getBooks(Long id, EntityManager em) {
        try {
            return em.createNamedQuery(UserDO.GET_BOOKS, Long.class).setParameter(1, id).getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    private long getRole(Long id, EntityManager em) {
        try {
            return em.createNamedQuery(UserDO.GET_ROLES, Long.class).setParameter(1, id).getResultList().get(0);
        } catch (NoResultException e) {
            return Long.MIN_VALUE;
        }
    }
}
