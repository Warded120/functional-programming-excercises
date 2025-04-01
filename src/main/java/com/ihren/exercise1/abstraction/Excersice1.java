package com.ihren.exercise1.abstraction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Excersice1 {
    protected Map<String, Long> findTweets(List<String> tweets) {
        return Stream.ofNullable(tweets)
                .flatMap(List::stream)
                .map(str -> str.split("[ .,!?:;]"))
                .flatMap(Arrays::stream)
                .filter(str -> str.startsWith("#"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
