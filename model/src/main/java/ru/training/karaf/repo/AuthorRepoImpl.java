package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.Author;
import ru.training.karaf.model.AuthorDO;
import ru.training.karaf.model.BookDO;
import ru.training.karaf.model.RoleDO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuthorRepoImpl implements AuthorRepo {

    private JpaTemplate template;

    public AuthorRepoImpl(JpaTemplate template) {
        this.template = template;
    }

    @Override
    public List<? extends Author> getAll() {
        return template.txExpr(em -> em.createNamedQuery(AuthorDO.GET_ALL, AuthorDO.class).getResultList());
    }

    @Override
    public List<? extends Author> getAll(String name, String lastName, int limit, int offset) {
        return template.txExpr(em -> em.createNamedQuery(AuthorDO.SEARCH, AuthorDO.class).getResultList());
    }

    @Override
    public void create(Author author) {
        AuthorDO authorToCreate = new AuthorDO();
        authorToCreate.setName(author.getName());
        authorToCreate.setLastName(author.getLastName());
        template.tx(em -> em.persist(authorToCreate));
    }

    @Override
    public void update(Long id, Author author) {
        template.tx(em -> {
            getById(id, em).ifPresent(authorToUpdate -> {
                authorToUpdate.setName(author.getName());
                authorToUpdate.setLastName(author.getLastName());

                em.merge(authorToUpdate);
            });
        });
    }

    public Optional<AuthorDO> getById(Long id, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(AuthorDO.GET_BY_ID, AuthorDO.class).setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<? extends Author> get(Long id) {
        return template.txExpr(em -> getById(id, em));
    }

    @Override
    public Optional<? extends Author> get(String name, String surname) {
        return template.txExpr(em -> getByName(name,surname, em));
    }

    @Override
    public List<Long> getBooks(Long id) {
        return template.txExpr(em -> getBooks(id, em));
    }

    private List<Long> getBooks(Long id, EntityManager em) {
        try {
            return em.createNamedQuery(AuthorDO.GET_BOOKS, Long.class).setParameter("id", id).getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public Optional<AuthorDO> getByName(String name, String surname,EntityManager em){
        try {
            return Optional.of(em.createNamedQuery(AuthorDO.GET_BY_NAME, AuthorDO.class).setParameter("name", name).setParameter("lastName",surname)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        template.tx(em -> getById(id, em).ifPresent(em::remove));
    }
}
