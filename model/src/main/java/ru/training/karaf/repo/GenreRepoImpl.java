package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.Genre;
import ru.training.karaf.model.GenreDO;
import ru.training.karaf.model.Role;
import ru.training.karaf.model.RoleDO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class GenreRepoImpl implements GenreRepo {
    private JpaTemplate template;

    public GenreRepoImpl(JpaTemplate template) {
        this.template = template;
    }

    @Override
    public List<? extends Genre> getAll() {
        return template.txExpr(em -> em.createNamedQuery(GenreDO.GET_ALL, GenreDO.class).getResultList());
    }

    @Override
    public List<? extends Genre> getAll(String name, int limit, int offset, String sortBy, String order) {
        if (order.equals("desc")) {
            return template.txExpr(em -> em.createNamedQuery(GenreDO.SEARCH_DESC).setParameter(1, name).setParameter(2, limit).setParameter(3, offset)
                    .getResultList());
        } else {
            return template.txExpr(em -> em.createNamedQuery(GenreDO.SEARCH_ASC).setParameter(1, name).setParameter(2, limit).setParameter(3, offset)
                    .getResultList());
        }
    }

    @Override
    public void create(Genre genre) {
        GenreDO genreToCreate = new GenreDO();
        genreToCreate.setName(genre.getName());
        template.tx(em -> em.persist(genreToCreate));
    }

    @Override
    public void update(Long id, Genre genre) {

    }

    @Override
    public Optional<? extends Genre> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<? extends Genre> get(String name) {
        return template.txExpr(em -> getByName(name, em));
    }

    public Optional<? extends Genre> getByName(String name, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(GenreDO.GET_BY_NAME, GenreDO.class).setParameter("name", name)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Long> showWithGenre(String name) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
