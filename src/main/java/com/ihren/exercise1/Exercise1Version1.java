package com.ihren.exercise1;

import com.ihren.exercise1.abstraction.Excersice1;

import java.util.*;
import java.util.stream.Collectors;

public class Exercise1Version1 extends Excersice1 {
    // returns hashtags with its count in text and sorted by count
    public Map<String, Long> collectTweets(List<String> tweets) {
        Map<String, Long> collected = findTweets(tweets);

        Map<String, Long> sorted = collected.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        //sorted.forEach((k, v) -> System.out.println(k + ": " + v));
        return sorted;
    }
}













