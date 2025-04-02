package com.ihren.exercise4;

import com.ihren.exercise4.models.DateTimeUtils;
import com.ihren.exercise4.models.Element;
import com.ihren.exercise4.models.ElementMapper;
import com.ihren.exercise4.models.Item;
import com.ihren.exercise4.models.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

class Exercise4Test {

    private final Exercise4 exercise4 = new Exercise4();

    private MockedStatic<ElementMapper> mockedStaticEm;
    private MockedStatic<DateTimeUtils> mockedStaticDtu;

    @BeforeEach
    void setUpElementMapperMockStatic() {
        mockedStaticEm = mockStatic(ElementMapper.class);
        mockedStaticDtu = mockStatic(DateTimeUtils.class);
    }

    @AfterEach
    void tearDownElementMapperMockStatic() {
        mockedStaticEm.close();
        mockedStaticDtu.close();
    }

    @Test
    @DisplayName("Should return filtered items")
    void convertReturnsFilteredItems() {
        //given
        Instant INSTANT = Instant.now();
        LocalDateTime DATE_TIME = LocalDateTime.now();
        Transaction transaction = new Transaction(
                Stream.of(
                                null,
                                new Item(null, null),
                                new Item(null, DATE_TIME),
                                new Item(new Element(null), DATE_TIME),
                                new Item(new Element(null), null),
                                new Item(new Element(INSTANT), DATE_TIME),
                                new Item(new Element(INSTANT), DATE_TIME)
                        )
                        .toList()
        );
        List<Item> expected = List.of(
                new Item(new Element(INSTANT), DATE_TIME),
                new Item(new Element(INSTANT), DATE_TIME),
                new Item(new Element(INSTANT), DATE_TIME)
        );

        mockedStaticDtu.when(() -> DateTimeUtils.getInstant(DATE_TIME))
                .thenReturn(INSTANT);
        mockedStaticEm.when(() -> ElementMapper.map(any(Element.class)))
                .thenAnswer(invocationOnMock -> Optional.ofNullable(invocationOnMock.getArgument(0)));

        //when
        List<Item> actual = exercise4.convert(transaction);

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return empty list when ElementMapper returns empty optional")
    void convertReturnsEmptyListWhenElementMapperReturnsEmptyOptional() {
        //given
        Instant INSTANT = Instant.now();
        LocalDateTime DATE_TIME = LocalDateTime.now();
        Transaction transaction = new Transaction(
                List.of(new Item(new Element(INSTANT), DATE_TIME))
        );
        List<Item> expected = List.of();

        mockedStaticDtu.when(() -> DateTimeUtils.getInstant(DATE_TIME))
                .thenReturn(INSTANT);
        mockedStaticEm.when(() -> ElementMapper.map(any(Element.class)))
                .thenReturn(Optional.empty());

        //when
        List<Item> actual = exercise4.convert(transaction);

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return empty list when DateTimeUtils returns null")
    void convertReturnsEmptyListWhenDateTimeUtilsReturnsNull() {
        //given
        Instant INSTANT = Instant.now();
        LocalDateTime DATE_TIME = LocalDateTime.now();
        Transaction transaction = new Transaction(
                List.of(new Item(new Element(INSTANT), DATE_TIME))
        );
        List<Item> expected = List.of(
                new Item(new Element(null), DATE_TIME)
        );

        mockedStaticDtu.when(() -> DateTimeUtils.getInstant(null))
                .thenReturn(INSTANT);
        mockedStaticEm.when(() -> ElementMapper.map(any(Element.class)))
                .thenAnswer(invocationOnMock -> Optional.ofNullable(invocationOnMock.getArgument(0)));

        //when
        List<Item> actual = exercise4.convert(transaction);

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return empty list when transaction is null")
    void convertReturnsEmptyListWhenTransactionIsNull() {
        //given
        //when
        List<Item> actual = exercise4.convert(null);

        //then
        assertEquals(List.of(), actual);
    }

    @Test
    @DisplayName("Should return empty list when items is null")
    void convertReturnsEmptyListWhenItemsIsNull() {
        //given
        //when
        List<Item> actual = exercise4.convert(new Transaction(null));

        //then
        assertEquals(List.of(), actual);
    }

    @Test
    @DisplayName("Should return empty list when items is empty")
    void convertReturnsEmptyListWhenItemsIsEmpty() {
        //given
        //when
        List<Item> actual = exercise4.convert(new Transaction(List.of()));

        //then
        assertEquals(List.of(), actual);
    }
}