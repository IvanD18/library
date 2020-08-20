package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.*;

public class CountRepoImpl implements CountRepo{
    private JpaTemplate template;

    public CountRepoImpl(JpaTemplate template) {
        this.template = template;
    }

    @Override
    public Long getCountOfReviews() {
        return template.txExpr(em -> em.createNamedQuery(ReviewDO.COUNT_REVIEWS, Long.class).getSingleResult());
    }

    @Override
    public Long getCountOfReviews(int rating) {
        return null;
    }

    @Override
    public Long getCountOfRoles() {
        return template.txExpr(em -> em.createNamedQuery(RoleDO.COUNT_ROLES, Long.class).getSingleResult());
    }

    @Override
    public Long getCountOfBooks() {
        return template.txExpr(em -> em.createNamedQuery(BookDO.COUNT_BOOKS, Long.class).getSingleResult());
    }

    @Override
    public Long getCountOfUsers() {
        return template.txExpr(em -> em.createNamedQuery(ReviewDO.COUNT_REVIEWS, Long.class).getSingleResult());
    }

    @Override
    public Long getCountOfUsers(int age) {
        return template.txExpr(em -> em.createNamedQuery(UserDO.COUNT_USERS, Long.class).getSingleResult());
    }

    @Override
    public Long getCountOfAuthors() {
        return template.txExpr(em -> em.createNamedQuery(AuthorDO.COUNT_AUTHORS, Long.class).getSingleResult());
    }
}
