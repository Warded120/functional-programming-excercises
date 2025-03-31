package com.ihren.exercise1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise1Version2Test {
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
        List<String> actual = Exercise1Version2.collectTweets();

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
        List<String> actual = Exercise1Version2.collectTweets();

        //then
        assertNotEquals(expected, actual);
    }
}