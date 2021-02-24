package com.cbk.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtil {

    /**
     *  求得list1中含有，而list2中并不存在的元素
     * @param list1
     * @param list2
     * @param <E>
     * @return
     */
    public static <E> List<E> differenceSet(List<E> list1, List<E> list2) {
        List<E> resultList = new ArrayList<>();

        Set<E> set = new HashSet<>(list2);
        for (E item : list1) {
            if (!set.contains(item)) {
                resultList.add(item);
            }
        }

        return resultList;
    }
}
