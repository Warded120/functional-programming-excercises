package com.ihren.exercise1;

import com.ihren.exercise1.abstraction.Excersice1;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;

public class Exercise1Version2 extends Excersice1 {
    // returns hashtags sorted by count without specifying, how many times it was used in text
    public List<String> collectTweets(List<String> tweets) {
        Map<String, Long> collected = findTweets(tweets);

        List<String> sorted = collected.entrySet().stream()
                .sorted(comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll);

        //sorted.forEach(System.out::println);
        return sorted;
    }
}














