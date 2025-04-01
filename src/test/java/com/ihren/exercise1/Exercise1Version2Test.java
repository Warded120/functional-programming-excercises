package com.ihren.exercise1;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise1Version2Test {
    Exercise1Version2 exercise1Version2 = new Exercise1Version2();

    @Test
    void collectTweetsShouldReturnSortedTweetsListByCountTest() {
        //given
        List<String> expected = List.of(
                "#Scala",
                "#Java",
                "#ChallengeEveryDay",
                "#cognitive"
        );

        //when
        List<String> actual = exercise1Version2.collectTweets(ModelUtils.TWEETS);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void collectTweetsShouldReturnFalseWhenListUnsorted() {
        //given
        List<String> expected = List.of(
                "#ChallengeEveryDay",
                "#Java",
                "#Scala",
                "#cognitive"
        );

        //when
        List<String> actual = exercise1Version2.collectTweets(ModelUtils.TWEETS);

        //then
        assertNotEquals(expected, actual);
    }

    @Test
    void collectTweetsShouldReturnEmptyListWhenTweetsIsNullTest() {
        assertEquals(List.of(), exercise1Version2.collectTweets(null));
    }
}