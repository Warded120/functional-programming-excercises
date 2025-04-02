package com.ihren.exercise1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Exercise1Version1Test {
    private final Exercise1Version1 exercise1Version1 = new Exercise1Version1();

    @Test
    @DisplayName("Should return a sorted tweet list by count")
    void collectTweetsReturnsSortedList() {
        //given
        List<String> tweets = List.of(
                "#Java and #Scala play a key role in AI and cognitive computing. IBM believes the future lies in cognitive technology.",
                "Here's an update on IBM’s backing of #Scala and Lightbend: http8/ibm-lightbend-partnership-enterprise",
                "IBM teams up with @lightbend to create a unified platform for #Java and #Scala #cognitive application development with tag #ChallengeEveryDay."
        );
        List<String> expected = List.of(
                "#Scala",
                "#Java",
                "#ChallengeEveryDay",
                "#cognitive"
        );

        //when
        List<String> actual = exercise1Version1.collectTweets(tweets);

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return false when the tweet list is unsorted")
    void collectTweetsReturnsFalseForUnsortedList() {
        //given
        List<String> tweets = List.of(
                "#Java and #Scala play a key role in AI and cognitive computing. IBM believes the future lies in cognitive technology.",
                "Here's an update on IBM’s backing of #Scala and Lightbend: http8/ibm-lightbend-partnership-enterprise",
                "IBM teams up with @lightbend to create a unified platform for #Java and #Scala #cognitive application development with tag #ChallengeEveryDay."
        );
        List<String> expected = List.of(
                "#ChallengeEveryDay",
                "#Java",
                "#Scala",
                "#cognitive"
        );

        //when
        List<String> actual = exercise1Version1.collectTweets(tweets);

        //then
        assertNotEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return an empty list when tweets are null")
    void collectTweetsReturnsEmptyListForNullTweets() {
        //when
        assertEquals(List.of(), exercise1Version1.collectTweets(null));
    }

    @Test
    @DisplayName("Should return an empty list when no tweets are found")
    void collectTweetsReturnsEmptyListForNoTweets() {
        //given
        List<String> tweets = List.of(
                "Java and Scala play a key role in AI and cognitive computing. IBM believes the future lies in cognitive technology.",
                "Here's an update on IBM’s backing of Scala and Lightbend: http8/ibm-lightbend-partnership-enterprise",
                "IBM teams up with @lightbend to create a unified platform for Java and Scala cognitive application development with tag ChallengeEveryDay."
        );
        List<String> expected = List.of();

        //when
        List<String> actual = exercise1Version1.collectTweets(tweets);

        //then
        assertEquals(expected, actual);
    }
}