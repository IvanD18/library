package ru.training.karaf.rest.comparator;

import ru.training.karaf.rest.dto.ReviewDTO;

import java.util.Comparator;

public class ReviewSurnameAscComparator implements Comparator<ReviewDTO> {
    @Override
    public int compare(ReviewDTO o1, ReviewDTO o2) {
        String s1 = o1.getUser().getLastName(), s2 = o2.getUser().getLastName();
        return s1.compareTo(s2);

    }
}
