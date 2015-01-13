package com.wake_e.tools;

import java.util.Comparator;

import com.wake_e.model.Slide;

/**
 * @brief Sort slides by their order
 * @author The Wake-E team
 */
public class SlidesComparator implements Comparator<Slide> {
    @Override
    public int compare(Slide s1, Slide s2) {
        return s1.getOrder().compareTo(s2.getOrder());
    }
}
