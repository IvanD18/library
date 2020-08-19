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
        List<Long> list = repo.showBooks(id);
        List<BookDTO> resultList = new ArrayList<>();
        for (Long aLong : list) {
            resultList.add(new BookDTO(bookRepo.get(aLong).get()));
        }
        return resultList;
    }

    @Override
    public void takeBook(Long id, Long bookId) {
        repo.addBook(id, bookRepo.get(bookId).get());
    }

    @Override
    public List<UserDTO> getAll() {

        List<UserDTO> result = repo.getAll().stream().map(u -> new UserDTO(u)).collect(Collectors.toList());

        return result;
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
        repo.update(login, user);
    }

    @Override
    public UserDTO get(String login) {
        return repo.get(login).map(u -> new UserDTO(u))
                .orElseThrow(() -> new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.APPLICATION_JSON_TYPE).entity("User not found").build()));
    }

    @Override
    public void delete(String login) {
        repo.delete(login);
    }

    @Override
    public void returnBook(Long id, Long bookId) {
        repo.removeBook(id, bookId);
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
