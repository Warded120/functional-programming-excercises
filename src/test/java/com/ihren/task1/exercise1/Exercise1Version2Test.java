package com.ihren.task1.exercise1;

import com.ihren.task1.exercise1.Exercise1Version2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Exercise1Version2Test {
    private final Exercise1Version2 exercise1Version2 = new Exercise1Version2();

    @Test
    @DisplayName("Should return sorted tweets by count")
    void collectTweetsReturnsSortedTweetsByCount() {
        //given
        List<String> tweets = List.of(
                "#Java and #Scala play a key role in AI and cognitive computing. IBM believes the future lies in cognitive technology.",
                "Here's an update on IBM’s backing of #Scala and Lightbend: http8/ibm-lightbend-partnership-enterprise",
                "IBM teams up with @lightbend to create a unified platform for #Java and #Scala #cognitive application development with tag #ChallengeEveryDay."
        );
        Map<String, Long> expected = new LinkedHashMap<>();
        expected.put("#Scala", 3L);
        expected.put("#Java", 2L);
        expected.put("#ChallengeEveryDay", 1L);
        expected.put("#cognitive", 1L);

        //when
        Map<String, Long> actual = exercise1Version2.collectTweets(tweets);

        //then
        assertEquals(expected.size(), actual.size(), "Map sizes are different");
        Assertions.assertThat(actual).containsExactlyEntriesOf(expected);
    }

    @Test
    @DisplayName("Should return empty map when tweets are null")
    void collectTweetsReturnsEmptyMapWhenTweetsAreNull() {
        //when
        assertEquals(Map.of(), exercise1Version2.collectTweets(null));
    }
}