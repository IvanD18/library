package ru.training.karaf.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@NamedQueries({
        @NamedQuery(name = RoleDO.GET_ALL, query = "SELECT u FROM RoleDO AS u"),
        @NamedQuery(name = RoleDO.GET_BY_ID, query = "SELECT u FROM RoleDO AS u WHERE u.id = :id"),
        @NamedQuery(name = RoleDO.GET_BY_NAME, query = "SELECT u FROM RoleDO AS u WHERE u.name = :name"),
        @NamedQuery(name = RoleDO.GET_WITH_TYPE, query = "SELECT u.id FROM RoleDO AS r join UserDO as u WHERE r.name = :name"),
        @NamedQuery(name = RoleDO.COUNT_ROLES, query = "SELECT COUNT(r) FROM RoleDO AS r")

})
@NamedNativeQueries({
        @NamedNativeQuery(name = RoleDO.SEARCH, query = "SELECT r.* FROM role AS r WHERE r.role_name LIKE ?1 LIMIT ?2 OFFSET ?3", resultClass =
                RoleDO.class)
})
public class RoleDO implements Role {

    public static final String GET_ALL = "RoleDO.getAll";

    public static final String GET_BY_ID = "RoleDO.getById";

    public static final String GET_BY_NAME = "RoleDO.getByName";

    public static final String GET_WITH_TYPE = "RoleDO.getWithType";

    public static final String COUNT_ROLES = "RoleDO.countRoles";

    public static final String SEARCH = "RoleDO.search";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    public RoleDO() {
    }

    public RoleDO(Role role) {
        this.name = role.getName();
        this.id = role.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



}
