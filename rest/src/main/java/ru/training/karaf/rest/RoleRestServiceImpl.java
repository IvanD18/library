package ru.training.karaf.rest;

import ru.training.karaf.repo.RoleRepo;

import ru.training.karaf.repo.UserRepo;
import ru.training.karaf.rest.dto.BookDTO;
import ru.training.karaf.rest.dto.RoleDTO;
import ru.training.karaf.rest.dto.UserDTO;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RoleRestServiceImpl implements RoleRestService {

    private RoleRepo repo;
    private UserRepo userRepo;

    public RoleRestServiceImpl(RoleRepo repo, UserRepo userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public List<RoleDTO> getAll() {
        return null;
    }

    @Override
    public void create(RoleDTO role) {
        repo.create(role);
    }

    @Override
    public void update(Long id, RoleDTO role) {

    }

    @Override
    public RoleDTO get(Long id) {
        return null;
    }

    @Override
    public RoleDTO getByName(String name) {
        return repo.get(name).map(u -> new RoleDTO(u))
                .orElseThrow(() -> new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorDTO("Role not found")).build()));
    }

    @Override
    public void delete(Long id) {

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
