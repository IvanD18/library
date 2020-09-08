package ru.training.karaf.rest;

import ru.training.karaf.repo.RoleRepo;

import ru.training.karaf.repo.UserRepo;
import ru.training.karaf.rest.dto.AuthorDTO;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;
import ru.training.karaf.rest.exception.NoPermissionsException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoleRestServiceImpl implements RoleRestService {

    private RoleRepo repo;
    private UserRepo userRepo;

    public RoleRestServiceImpl(RoleRepo repo, UserRepo userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public List<RoleDTO> getAll(String name, int limit, int offset) {
        name = name == null ? "%" : name;
        limit = (limit == 0) ? 10 : limit;
        offset = offset > 0 ? limit * (offset - 1) : 0;
        List<RoleDTO> result = repo.getAll(name, limit, offset).stream().map(r -> new RoleDTO(r)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void create(RoleDTO role) {
        if (ServiceUtils.isAdmin()) {
            repo.create(role);
        } else {
            throw new NoPermissionsException(ServiceUtils.doItMessage());
        }
    }

    @Override
    public void update(Long id, RoleDTO role) {

        if (ServiceUtils.isAdmin()) {
            repo.update(id, role);
        } else {
            throw new NoPermissionsException(ServiceUtils.updateMessage());
        }
    }

    @Override
    public RoleDTO get(Long id) {
        return new RoleDTO(repo.get(id).get());
    }

    @Override
    public RoleDTO getByName(String name) {
        return repo.get(name).map(u -> new RoleDTO(u))
                .orElseThrow(() -> new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorDTO("Role not found")).build()));
    }

    @Override
    public void delete(Long id) {
        if (ServiceUtils.isAdmin()) {
            repo.delete(id);
        } else {
            throw new NoPermissionsException(ServiceUtils.deleteMessage());
        }
    }

    @Override
    public List<UserDTO> showWithType(String name) {
        List<Long> list = repo.showWithType(name);
        List<UserDTO> resultList = new ArrayList<>();
        for (Long aLong : list) {
            resultList.add(new UserDTO(userRepo.get(aLong).get()));
        }
        return resultList;
    }
}
