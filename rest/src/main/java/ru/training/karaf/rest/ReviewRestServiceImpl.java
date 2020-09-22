package ru.training.karaf.rest;

import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.repo.ReviewRepo;
import ru.training.karaf.repo.UserRepo;
import ru.training.karaf.rest.comparator.ReviewSurnameAscComparator;
import ru.training.karaf.rest.comparator.ReviewSurnameDescComparator;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.ReviewDTO;
import ru.training.karaf.rest.dto.UserDTO;
import ru.training.karaf.rest.exception.NoPermissionsException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewRestServiceImpl implements ReviewRestService {
    ReviewRepo repo;
    UserRepo userRepo;
    BookRepo bookRepo;

    public ReviewRestServiceImpl(ReviewRepo repo, UserRepo userRepo, BookRepo bookRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<ReviewDTO> getAll(int rating, String title, String login, int limit, int offset, String mode, String sort, String order) {
        limit = (limit == 0) ? 10 : limit;
        offset = offset > 0 ? limit * (offset - 1) : 0;
        title = title == null ? "%" : "%" + title + "%";
        login = login == null ? "%" : login;
        mode = mode == null ? "" : mode;
        sort = sort == null ? "" : sort;
        order = order == null ? "" : order;
        List<ReviewDTO> result = repo.getAll(rating, title, login, limit, offset, mode).stream().map(r -> new ReviewDTO(r)).collect(
                Collectors.toList());
        if (sort.equals("rating") && order.equals("asc")) {
            result.sort(Comparator.comparingInt(ReviewDTO::getRating));
            return result;
        }
        if (sort.equals("rating") && order.equals("desc")) {
            result.sort(Comparator.comparingInt(ReviewDTO::getRating));
            return result;
        }
        if (sort.equals("surname") && order.equals("asc")) {
            result.sort(Comparator.comparing(ReviewDTO::getSurname));
            return result;
        }
        if (sort.equals("surname") && order.equals("desc")) {
            result.sort(new ReviewSurnameDescComparator());
            return result;
        }
        if (sort.equals("title") && order.equals("desc")) {
            Collections.sort(result, (o1, o2) -> {
                char i, j;
                int count = 0;
                int rule = 0;
                String s1 = o1.getBook().getTitle(), s2 = o2.getBook().getTitle();
                while (rule == 0 && count != s1.length() && count != s2.length()) {
                    i = s1.charAt(count);
                    j = s2.charAt(count);
                    rule = Character.compare(i, j);
                    count++;
                }
                if (rule > 0) {
                    return -1;
                }
                if (rule < 0) {
                    return 1;
                } else {
                    return 0;
                }
            });
            return result;
        }
        if (sort.equals("title") && order.equals("asc")) {
            Collections.sort(result, (o1, o2) -> {
                char i, j;
                int count = 0;
                int rule = 0;
                String s1 = o1.getBook().getTitle(), s2 = o2.getBook().getTitle();
                while (rule == 0 && count != s1.length() && count != s2.length()) {
                    i = s1.charAt(count);
                    j = s2.charAt(count);
                    rule = Character.compare(i, j);
                    count++;
                }
                if (rule > 0) {
                    return 1;
                }
                if (rule < 0) {
                    return -1;
                } else {
                    return 0;
                }
            });
        }
        return result;
    }

    @Override
    public void create(ReviewDTO review) {
        if (ServiceUtils.isAdmin()) {
            review.setUser(new UserDTO(userRepo.get(review.getUser().getLogin()).get()));
            review.setBook(new BookDTO(bookRepo.getByTitle(review.getBook().getTitle()).get()));
            repo.create(review);
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public void update(Long id, ReviewDTO review) {
        if (ServiceUtils.isAdmin()) {
            repo.update(id, review);
        } else {
            throw new NoPermissionsException(ServiceUtils.updateMessage());
        }
    }

    @Override
    public ReviewDTO get(Long id) {
        return new ReviewDTO(repo.get(id).get());
    }

    @Override
    public void delete(Long id) {
        if (ServiceUtils.isAdmin()) {
            repo.delete(id);
        } else {
            throw new NoPermissionsException(ServiceUtils.deleteMessage());
        }
    }
}
