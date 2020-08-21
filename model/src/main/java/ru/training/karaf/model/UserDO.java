package ru.training.karaf.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = UserDO.GET_ALL, query = "SELECT u FROM UserDO AS u"),
        @NamedQuery(name = UserDO.GET_BY_LOGIN, query = "SELECT u FROM UserDO AS u WHERE u.login = :login"),
        @NamedQuery(name = UserDO.GET_BY_ID, query = "SELECT u FROM UserDO AS u WHERE u.id = :id"),
        @NamedQuery(name = UserDO.COUNT_USERS, query = "SELECT COUNT(u) FROM UserDO AS u"),
        @NamedQuery(name = UserDO.COUNT_USERS_WITH_AGE, query = "SELECT COUNT(u) FROM UserDO AS u WHERE u.age > :age")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = UserDO.DELETE_BOOK, query = "DELETE FROM users_book WHERE userdo_id = :id AND book_id = :book_id"),
        @NamedNativeQuery(name = UserDO.COUNT_BOOKS, query = "Select count(*) from users_book where userdo_id = ?"),
        @NamedNativeQuery(name = UserDO.GET_ROLES, query = "select r.id from role as r where r.id in (select u.role_id from " +
                "users as u where u.id = ?)"),
        @NamedNativeQuery(name = UserDO.GET_BOOKS, query = "select b.id from book as b where b.id in (select ub.book_id from " +
                "users_book as ub where userdo_id = ?)")
})
public class UserDO implements User {
    public static final String GET_ALL = "Users.getAll";
    public static final String GET_BY_LOGIN = "Users.getByLogin";
    public static final String GET_BY_ID = "Users.getById";
    public static final String GET_BOOKS = "Users.getBooks";
    public static final String DELETE_BOOK = "Users.deleteBook";
    public static final String GET_ROLES = "Users.getRoles";
    public static final String COUNT_USERS = "Users.countUsers";
    public static final String COUNT_BOOKS = "Users.countBooks";
    public static final String COUNT_USERS_WITH_AGE = "Users.countUsersWithAge";

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    private Integer age;
    private String address;
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BookDO> book;

    @ManyToOne(fetch = FetchType.EAGER)
    private RoleDO role;

    @ElementCollection
    @CollectionTable(name = "user_properties",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> properties;

    public List<BookDO> getBook() {
        return book;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setBook(List<BookDO> book) {
        this.book = book;
    }

    public UserDO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        //        List<? extends Book> list= user.getBook();
        //        List<BookDO> result=new ArrayList<>();
        //        for (Book book1 : list) {
        //            result.add(new BookDO(book1));
        //        }
        //        this.book= result;
    }

    public UserDO() {
    }

    public RoleDO getRole() {
        return role;
    }

    public void setRole(RoleDO role) {
        this.role = role;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<String> getProperties() {
        return properties;
    }

    public void setProperties(Set<String> properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserDO other = (UserDO) obj;
        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals(other.address)) {
            return false;
        }
        if (age == null) {
            if (other.age != null) {
                return false;
            }
        } else if (!age.equals(other.age)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (properties == null) {
            if (other.properties != null) {
                return false;
            }
        } else if (!properties.equals(other.properties)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserDO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", login=" + login
                + ", age=" + age + ", address=" + address + ", properties=" + properties + "]";
    }
}
