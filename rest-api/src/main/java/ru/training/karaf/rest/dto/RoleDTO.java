package ru.training.karaf.rest.dto;

import ru.training.karaf.model.Role;




public class RoleDTO implements Role {
    private String name;
    private Long id;

    public RoleDTO() {
    }

    public RoleDTO(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public RoleDTO(Role role) {
        this.name = role.getName();
        this.id=role.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
