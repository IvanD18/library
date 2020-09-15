package ru.training.karaf.rest.comparator;

import ru.training.karaf.rest.dto.UserDTO;

import java.util.Comparator;

public class UserAgeComparator implements Comparator<UserDTO> {
    @Override
    public int compare(UserDTO o1, UserDTO o2) {
        return o2.getAge()-o1.getAge();
    }
}
