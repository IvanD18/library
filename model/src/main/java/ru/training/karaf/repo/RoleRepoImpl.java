package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoleRepoImpl implements RoleRepo {

    private JpaTemplate template;

    public RoleRepoImpl(JpaTemplate template) {
        this.template = template;
    }

    @Override
    public List<? extends Role> getAll() {
        return template.txExpr(em -> em.createNamedQuery(RoleDO.GET_ALL, RoleDO.class).getResultList());
    }

    @Override
    public List<? extends Role> getAll(String name, int limit, int offset) {
        return template.txExpr(em -> em.createNamedQuery(RoleDO.SEARCH, RoleDO.class).setParameter(1, name).setParameter(2, limit)
                .setParameter(3, offset).getResultList());
    }

    @Override
    public void create(Role role) {
        RoleDO roleToCreate = new RoleDO();
        roleToCreate.setName(role.getName());
        template.tx(em -> em.persist(roleToCreate));
    }

    @Override
    public void update(Long id, Role role) {
        template.tx(em -> {
            getById(id, em).ifPresent(roleToUpdate -> {
                roleToUpdate.setName(role.getName());
                em.merge(roleToUpdate);
            });
        });
    }

    public Optional<RoleDO> getById(Long id, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(RoleDO.GET_BY_ID, RoleDO.class).setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<? extends Role> get(Long id) {
        return template.txExpr(em -> getById(id, em));
    }

    @Override
    public Optional<? extends Role> get(String name) {
        return template.txExpr(em -> getByName(name, em));
    }

    @Override
    public List<Long> showWithType(String name) {
        return template.txExpr(em -> getUsers(name, em));
    }

    private List<Long> getUsers(String name, EntityManager em) {
        try {
            return em.createNamedQuery(RoleDO.GET_WITH_TYPE, Long.class).setParameter("name", name).getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public Optional<? extends Role> getByName(String name, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(RoleDO.GET_BY_NAME, RoleDO.class).setParameter("name", name)
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
