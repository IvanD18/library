package ru.training.karaf.rest.comparator;

import ru.training.karaf.rest.dto.UserDTO;

import java.util.Comparator;

public class UserAddressDescComparator implements Comparator<UserDTO> {
    @Override
    public int compare(UserDTO o1, UserDTO o2) {
        char i, j;
        int count = 0;
        int rule = 0;
        String s1 = o1.getAddress(), s2 = o2.getAddress();
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
