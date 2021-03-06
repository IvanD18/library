package ru.training.karaf.repo;

import org.apache.aries.jpa.template.JpaTemplate;
import ru.training.karaf.model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class ReviewRepoImpl implements ReviewRepo {

    private JpaTemplate template;

    public ReviewRepoImpl(JpaTemplate template) {
        this.template = template;
    }

    @Override
    public List<? extends Review> getAll() {
        return template.txExpr(em -> em.createNamedQuery(ReviewDO.GET_ALL, ReviewDO.class).getResultList());
    }

    @Override
    public List<? extends Review> getAll(int rating, String title, String login, int limit, int offset, String mode) {
        if (mode.equalsIgnoreCase("equally")) {
            return template.txExpr(em -> em.createNamedQuery(ReviewDO.SEARCH_WITH_EQUAL_RATING, ReviewDO.class).setParameter("rating", rating)
                    .setParameter("title",
                            title)
                    .setParameter("login", login).setParameter("limit", limit).setParameter("offset", offset).getResultList());
        }
        if (mode.equalsIgnoreCase("less")) {
            return template.txExpr(em -> em.createNamedQuery(ReviewDO.SEARCH_WITH_LESS_RATING, ReviewDO.class).setParameter("rating", rating)
                    .setParameter("title",
                            title)
                    .setParameter("login", login).setParameter("limit", limit).setParameter("offset", offset).getResultList());
        } else {
            return template.txExpr(em -> em.createNamedQuery(ReviewDO.SEARCH_WITH_MORE_RATING, ReviewDO.class).setParameter("rating", rating)
                    .setParameter("title",
                            title)
                    .setParameter("login", login).setParameter("limit", limit).setParameter("offset", offset).getResultList());
        }
    }

    @Override
    public void create(Review review) {
        ReviewDO reviewToCreate = new ReviewDO();
        reviewToCreate.setComment(review.getComment());
        reviewToCreate.setRating(review.getRating());
        reviewToCreate.setBook(new BookDO(review.getBook()));
        reviewToCreate.setUser(new UserDO(review.getUser()));
        template.tx(em -> em.persist(reviewToCreate));
    }

    @Override
    public void update(Long id, Review review) {
        template.tx(em -> {
            getById(id, em).ifPresent(reviewToUpdate -> {
                reviewToUpdate.setComment(review.getComment());
                reviewToUpdate.setRating(review.getRating());
                em.merge(reviewToUpdate);
            });
        });
    }

    public Optional<ReviewDO> getById(Long id, EntityManager em) {
        try {
            return Optional.of(em.createNamedQuery(ReviewDO.GET_BY_ID, ReviewDO.class).setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<? extends Review> get(Long id) {
        return template.txExpr(em -> getById(id, em));
    }

    @Override
    public void delete(Long id) {
        template.tx(em -> getById(id, em).ifPresent(em::remove));
    }
}
