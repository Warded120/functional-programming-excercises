package com.ihren.exercise1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class Exercise1Version2 {
    public static final List<String> tweets = List.of(
            "#Java and #Scala play a key role in AI and cognitive computing. IBM believes the future lies in cognitive technology.",
            "Here's an update on IBM’s backing of #Scala and Lightbend: http8/ibm-lightbend-partnership-enterprise",
            "IBM teams up with @lightbend to create a unified platform for #Java and #Scala #cognitive application development with tag #ChallengeEveryDay."
    );

    // returns hashtags sorted by count without specifying, how many times it was used in text
    public static List<String> collectTweets() {
        Map<String, Long> collected = tweets.stream()
                .map(str -> str.split("[ .,!?:;]"))
                .flatMap(Arrays::stream)
                .filter(str -> str.startsWith("#"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

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
