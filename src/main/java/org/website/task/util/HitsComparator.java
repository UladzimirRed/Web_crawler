package org.website.task.util;

import java.util.Comparator;

public class HitsComparator implements Comparator<String[]> {

    @Override
    public int compare(String[] o1, String[] o2) {
        int last1 = Integer.parseInt(o1[o1.length - 1]);
        int last2 = Integer.parseInt(o2[o2.length - 1]);
        if (last1 > last2) {
            return 1;
        } else if (last1 < last2) {
            return -1;
        }
        return 0;
    }
}
