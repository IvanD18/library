package ru.training.karaf.rest.comparator;

import java.util.Comparator;

import ru.training.karaf.rest.dto.ReviewDTO;

public class ReviewSurnameDescComparator implements Comparator<ReviewDTO> {
    @Override
    public int compare(ReviewDTO o1, ReviewDTO o2) {
        char i, j;
        int count = 0;
        int rule = 0;
        String s1 = o1.getUser().getLastName(), s2 = o2.getUser().getLastName();
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
