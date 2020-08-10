package ru.training.karaf.model;

import java.util.List;
import java.util.Set;

public interface User {
    String getFirstName();

    String getLastName();

    String getLogin();

    Integer getAge();

    String getAddress();

    Set<String> getProperties();

    Long getId();

    List<? extends Book> getBook();



    Role getRole();
}
