package com.ihren.task1.exercise1;

import com.ihren.task1.exercise1.abstraction.Exercise1;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Exercise1Version2 extends Exercise1 {

    /**
     * alternative method with specifying tweets count in a result.<br>
     * to see primary method see {@link Exercise1Version1}
     */
    public Map<String, Long> collectTweets(List<String> tweets) {
        return findTweets(tweets).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}













