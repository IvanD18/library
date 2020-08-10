package ru.training.karaf.repo;

import ru.training.karaf.model.Role;
import ru.training.karaf.model.User;

import java.util.List;
import java.util.Optional;

public interface RoleRepo {
    List<? extends Role> getAll();

    void create(Role role);

    void update(Long id, Role role);

    Optional<? extends Role> get(Long id);


    Optional<? extends Role> get(String name);

    List<Long> showWithType(String name);


    void delete(Long id);
}
