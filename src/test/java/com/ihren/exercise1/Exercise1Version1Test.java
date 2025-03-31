package com.ihren.exercise1;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class Exercise1Version1Test {

    @Test
    void collectTweetsShouldReturnSortedTweetsByCountTest() {
        //given
        LinkedHashMap<String, Long> expected = new LinkedHashMap<>();
        expected.put("#Scala", 3L);
        expected.put("#Java", 2L);
        expected.put("#ChallengeEveryDay", 1L);
        expected.put("#cognitive", 1L);

        //when
        LinkedHashMap<String, Long> actual = (LinkedHashMap<String, Long>) Exercise1Version1.collectTweets();

        //then
        assertEquals(expected.size(), actual.size(), "Map sizes are different");

        Iterator<Map.Entry<String, Long>> expectedIterator = expected.entrySet().iterator();
        Iterator<Map.Entry<String, Long>> actualIterator = actual.entrySet().iterator();

        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            Map.Entry<String, Long> expectedEntry = expectedIterator.next();
            Map.Entry<String, Long> actualEntry = actualIterator.next();

            assertEquals(expectedEntry, actualEntry, "Maps' elements are different");
        }
    }

    @Test
    void collectTweetsShouldFailWhenTweetsNotSortedByCountTest() {
        //given
        LinkedHashMap<String, Long> expected = new LinkedHashMap<>();
        expected.put("#Java", 2L);
        expected.put("#ChallengeEveryDay", 1L);
        expected.put("#cognitive", 1L);
        expected.put("#Scala", 3L);

        //when
        LinkedHashMap<String, Long> actual = (LinkedHashMap<String, Long>) Exercise1Version1.collectTweets();

        //then
        assertEquals(expected.size(), actual.size(), "Map sizes are different");

        Iterator<Map.Entry<String, Long>> expectedIterator = expected.entrySet().iterator();
        Iterator<Map.Entry<String, Long>> actualIterator = actual.entrySet().iterator();
        boolean linkedHashMapOrdered = true;

        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            Map.Entry<String, Long> expectedEntry = expectedIterator.next();
            Map.Entry<String, Long> actualEntry = actualIterator.next();

            if(!expectedEntry.equals(actualEntry)) {
                linkedHashMapOrdered = false;
            }
        }
        assertFalse(linkedHashMapOrdered);
    }
}