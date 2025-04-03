package com.ihren.task1.exercise1;

import com.ihren.task1.exercise1.abstraction.Exercise1;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Exercise1Version1 extends Exercise1 {
    /**
     * Primary method to find tweets in a {@link List} of {@link String}'s
     */
    public List<String> collectTweets(List<String> tweets) {
        return findTweets(tweets).entrySet().stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll);
    }
}














