package ru.training.karaf.rest.comparator;

import java.util.Comparator;

import ru.training.karaf.rest.dto.UserDTO;

public class UserLoginDescComparator implements Comparator<UserDTO> {
    @Override
    public int compare(UserDTO o1, UserDTO o2) {
        char i, j;
        int count = 0;
        int rule = 0;
        String s1 = o1.getLogin(), s2 = o2.getLogin();
        while (rule == 0 && count != s1.length() && count != s2.length()) {
            i = s1.charAt(count);
            j = s2.charAt(count);
            rule = Character.compare(i, j);
            count++;
        }
        if (rule > 0) {
            return -1;
        }
        if (rule < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
