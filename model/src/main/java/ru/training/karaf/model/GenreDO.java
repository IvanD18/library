package ru.training.karaf.model;

import javax.persistence.*;

@Entity
@Table(name = "genre")
@NamedQueries({
        @NamedQuery(name = GenreDO.GET_ALL, query = "SELECT g FROM GenreDO AS g"),
        @NamedQuery(name = GenreDO.GET_BY_ID, query = "SELECT g FROM GenreDO AS g WHERE g.id = :id"),
        @NamedQuery(name = GenreDO.GET_BY_NAME, query = "SELECT g FROM GenreDO AS g WHERE g.name = :name"),
        @NamedQuery(name = GenreDO.COUNT_GENRES, query = "SELECT COUNT(g) FROM GenreDO AS g")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = GenreDO.SEARCH_ASC, query = "SELECT g.* FROM genre AS g WHERE g.genre_name LIKE #genre ORDER BY genre_name ASC " +
                "LIMIT " +
                " #limit OFFSET #offset", resultClass =
                GenreDO.class),
        @NamedNativeQuery(name = GenreDO.SEARCH_DESC, query = "SELECT g.* FROM genre AS g WHERE g.genre_name LIKE #genre ORDER BY genre_name DESC " +
                " LIMIT #limit OFFSET #offset", resultClass =
                GenreDO.class)
})
public class GenreDO implements Genre {

    public static final String GET_ALL = "GenreDO.getAll";

    public static final String GET_BY_ID = "GenreDO.getById";

    public static final String GET_BY_NAME = "GenreDO.getByName";

    public static final String GET_WITH_TYPE = "GenreDO.getWithType";

    public static final String COUNT_GENRES = "GenreDO.countGenres";

    public static final String SEARCH_ASC = "GenreDO.searchAsc";
    public static final String SEARCH_DESC = "GenreDO.searchDesc";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "genre_name", nullable = false, unique = true)
    private String name;

    public GenreDO() {
    }

    public GenreDO(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
