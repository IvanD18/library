package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.*;

public class CountRepoImpl implements CountRepo{
    private JpaTemplate template;

    public CountRepoImpl(JpaTemplate template) {
        this.template = template;
    }



    @Override
    public Long getCountOfReviews(int rating) {
        return template.txExpr(em -> em.createNamedQuery(ReviewDO.COUNT_REVIEWS_WITH_RATING, Long.class).setParameter("rating",rating).getSingleResult());
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
    public Long getCountOfBooksForUser(Long user_id) {
        return template.txExpr(em -> em.createNamedQuery(UserDO.COUNT_BOOKS, Long.class).setParameter(1,user_id).getSingleResult());
    }

    @Override
    public Long getCountOfAvailableBooks() {
        return template.txExpr(em -> em.createNamedQuery(BookDO.COUNT_AVAILABLE_BOOKS, Long.class).getSingleResult());
    }

    @Override
    public Long getCountOfNotAvailableBooks() {
        return template.txExpr(em -> em.createNamedQuery(BookDO.COUNT_NOT_AVAILABLE_BOOKS, Long.class).getSingleResult());
    }

    @Override
    public Long getCountOfUsers(int age) {
        return template.txExpr(em -> em.createNamedQuery(UserDO.COUNT_USERS_WITH_AGE, Long.class).setParameter("age",age).getSingleResult());
    }

    @Override
    public Long getCountOfAuthors() {
        return template.txExpr(em -> em.createNamedQuery(AuthorDO.COUNT_AUTHORS, Long.class).getSingleResult());
    }
}
