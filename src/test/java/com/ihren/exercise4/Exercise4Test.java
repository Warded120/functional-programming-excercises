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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

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

        Element element3 = mock(Element.class);
        when(element3.getStartDateTime()).thenReturn(null);

        Element element4 = mock(Element.class);
        when(element4.getStartDateTime()).thenReturn(null);

        Element element5 = mock(Element.class);
        when(element5.getStartDateTime()).thenReturn(INSTANT);

        Element element6 = mock(Element.class);
        when(element6.getStartDateTime()).thenReturn(INSTANT);

        Item item1 = mock(Item.class);
        when(item1.element()).thenReturn(null);
        when(item1.startDateTime()).thenReturn(null);

        Item item2 = mock(Item.class);
        when(item2.element()).thenReturn(null);
        when(item2.startDateTime()).thenReturn(DATE_TIME);

        Item item3 = mock(Item.class);
        when(item3.element()).thenReturn(element3);
        when(item3.startDateTime()).thenReturn(DATE_TIME);

        Item item4 = mock(Item.class);
        when(item4.element()).thenReturn(element4);
        when(item4.startDateTime()).thenReturn(null);

        Item item5 = mock(Item.class);
        when(item5.element()).thenReturn(element5);
        when(item5.startDateTime()).thenReturn(DATE_TIME);

        Item item6 = mock(Item.class);
        when(item6.element()).thenReturn(element6);
        when(item6.startDateTime()).thenReturn(DATE_TIME);

        Transaction transaction = new Transaction(
                Stream.of(null, item1, item2, item3, item4, item5, item6)
                .toList()
        );
        List<Item> expected = List.of(item3, item5, item6);

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

        Element element1 = mock(Element.class);
        when(element1.getStartDateTime()).thenReturn(INSTANT);

        Item item1 = mock(Item.class);
        when(item1.element()).thenReturn(element1);
        when(item1.startDateTime()).thenReturn(DATE_TIME);

        Transaction transaction1 = mock(Transaction.class);
        when(transaction1.items()).thenReturn(List.of(item1));

        List<Item> expected = List.of();

        mockedStaticDtu.when(() -> DateTimeUtils.getInstant(DATE_TIME))
                .thenReturn(INSTANT);
        mockedStaticEm.when(() -> ElementMapper.map(any(Element.class)))
                .thenReturn(Optional.empty());

        //when
        List<Item> actual = exercise4.convert(transaction1);

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return empty list when DateTimeUtils returns null")
    void convertReturnsEmptyListWhenDateTimeUtilsReturnsNull() {
        //given
        Instant INSTANT = Instant.now();
        LocalDateTime DATE_TIME = LocalDateTime.now();

        Element element1 = mock(Element.class);
        when(element1.getStartDateTime()).thenReturn(INSTANT);
        when(element1.getStartDateTime()).thenReturn(null);

        Item item1 = mock(Item.class);
        when(item1.element()).thenReturn(element1);
        when(item1.startDateTime()).thenReturn(DATE_TIME);

        Transaction transaction1 = mock(Transaction.class);
        when(transaction1.items()).thenReturn(List.of(item1));


        List<Item> expected = List.of(item1);

        mockedStaticDtu.when(() -> DateTimeUtils.getInstant(null))
                .thenReturn(INSTANT);
        mockedStaticEm.when(() -> ElementMapper.map(any(Element.class)))
                .thenAnswer(invocationOnMock -> Optional.ofNullable(invocationOnMock.getArgument(0)));

        //when
        List<Item> actual = exercise4.convert(transaction1);

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
        Transaction transaction1 = mock(Transaction.class);
        when(transaction1.items()).thenReturn(null);
        //when
        List<Item> actual = exercise4.convert(transaction1);

        //then
        assertEquals(List.of(), actual);
    }

    @Test
    @DisplayName("Should return empty list when items is empty")
    void convertReturnsEmptyListWhenItemsIsEmpty() {
        //given
        Transaction transaction1 = mock(Transaction.class);
        when(transaction1.items()).thenReturn(List.of());

        //when
        List<Item> actual = exercise4.convert(transaction1);

        //then
        assertEquals(List.of(), actual);
    }
}