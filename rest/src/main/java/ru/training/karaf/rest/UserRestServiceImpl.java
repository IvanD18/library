package ru.training.karaf.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.subject.Subject;
import ru.training.karaf.model.Book;
import ru.training.karaf.repo.BookRepo;
import ru.training.karaf.repo.RoleRepo;
import ru.training.karaf.repo.UserRepo;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.LoginfoDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;
import ru.training.karaf.rest.exception.NoPermissionsException;

public class UserRestServiceImpl implements UserRestService {

    private DefaultPasswordService passwordService = new DefaultPasswordService();

    private UserRepo repo;
    private RoleRepo roleRepo;
    private BookRepo bookRepo;

    public void setBookRepo(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public void setRepo(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<BookDTO> showBooks(Long id) {
        if (ServiceUtils.isAdmin() || (id == ServiceUtils.getId())) {
            List<Long> list = repo.showBooks(id);
            List<BookDTO> resultList = new ArrayList<>();
            for (Long aLong : list) {
                resultList.add(new BookDTO(bookRepo.get(aLong).get()));
            }
            return resultList;
        } else {
            throw new NoPermissionsException(ServiceUtils.viewMessage());
        }
    }

    @Override
    public String takeBook(Long id, Long bookId) {
        if (ServiceUtils.isUser()) {
            Book book = bookRepo.get(bookId).get();
            BookDTO bookDTO = new BookDTO(book);
            if (book.getAvailability() == true) {
                bookDTO.setAvailability(false);
                bookRepo.update(book.getId(), bookDTO);
                repo.addBook(id, bookRepo.get(bookId).get());
                return "success";
            }
            return "not available";
        } else {
            throw new NoPermissionsException("Please login as a user");
        }
    }

    //    @Override
    //    public List<UserDTO> getAll() throws NoPermissionsException {
    //        if (ServiceUtils.isAdmin()) {
    //            List<UserDTO> result = repo.getAll().stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
    //            return result;
    //        } else {
    //            throw new NoPermissionsException(ServiceUtils.viewMessage());
    //        }
    //    }

    @Override
    public List<UserDTO> getAll(int age, String ratio, String role, int limit, int offset, String address) throws Exception {
        ratio = (ratio == null) ? "%" : ratio;
        address = (address == null) ? "%" : address;
        limit = (limit == 0) ? 10 : limit;
        offset = offset > 0 ? limit * (offset - 1) : 0;
        role = role == null ? "%" : role;

        if (ServiceUtils.isAdmin()) {
            List<UserDTO> result = repo.searchByAge(age, ratio, role, limit, offset, address).stream().map(u -> new UserDTO(u)).collect(
                    Collectors.toList());
            return result;
        } else {
            throw new NoPermissionsException(ServiceUtils.viewMessage());
        }
    }

    @Override
    public UserDTO create(UserDTO user) {
        repo.get(user.getLogin()).ifPresent(user1 -> {
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorDTO("User found ...")).build());
        });
        user.setPassword(passwordService.encryptPassword(user.getPassword()));
        user.setRole(new RoleDTO(user.getRole().getName(), roleRepo.get(user.getRole().getName()).get().getId()));
        repo.create(user);
        return user;
    }

    @Override
    public void update(String login, UserDTO user) {
        if (ServiceUtils.isAdmin() || (login == ServiceUtils.getLogin())) {
            repo.update(login, user);
        } else {
            throw new NoPermissionsException(ServiceUtils.updateMessage());
        }
    }

    @Override
    public UserDTO get(String login) {
        if (ServiceUtils.isAdmin()) {
            return repo.get(login).map(u -> new UserDTO(u))
                    .orElseThrow(() -> new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                            .type(MediaType.APPLICATION_JSON_TYPE).entity("User not found").build()));
        } else {
            throw new NoPermissionsException(ServiceUtils.viewMessage());
        }
    }

    @Override
    public void delete(String login) {
        if (ServiceUtils.isAdmin() || (login == ServiceUtils.getLogin())) {
            repo.delete(login);
        } else {
            throw new NoPermissionsException(ServiceUtils.deleteMessage());
        }
    }

    @Override
    public void returnBook(Long id, Long bookId) {
        if (ServiceUtils.isUser()) {
            repo.removeBook(id, bookId);
            //TODO проверка возвращена ли книга?
            Book book = bookRepo.get(bookId).get();
            BookDTO bookDTO = new BookDTO(book);
            bookDTO.setAvailability(true);
            bookRepo.update(book.getId(), bookDTO);
            Subject currentUser = SecurityUtils.getSubject();
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public UserDTO login(LoginfoDTO loginfo) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginfo.getLogin(), loginfo.getPassword());
        token.setRememberMe(loginfo.isRememberMe());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        System.out.println(currentUser);
        return new UserDTO(repo.get((String) currentUser.getPrincipal()).get());
    }
}
