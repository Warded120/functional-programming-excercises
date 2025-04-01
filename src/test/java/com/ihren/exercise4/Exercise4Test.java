package com.ihren.exercise4;

import com.ihren.exercise4.models.DateTimeUtils;
import com.ihren.exercise4.models.Element;
import com.ihren.exercise4.models.ElementMapper;
import com.ihren.exercise4.models.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.time.LocalDateTime;
import java.util.List;

import static com.ihren.exercise4.ModelUtils.DATE_TIME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Exercise4Test {

    Exercise4 exercise4 = new Exercise4();

    MockedStatic<ElementMapper> mockedStaticEm;
    MockedStatic<DateTimeUtils> mockedStaticDtu;

    @BeforeEach
    void setUpElementMapperMockStatic() {
        mockedStaticEm = mockStatic(ElementMapper.class);
    }

    @BeforeEach
    void setUpDateTimeUtilsMockStatic() {
        mockedStaticDtu = mockStatic(DateTimeUtils.class);
    }

    @AfterEach
    void tearDownElementMapperMockStatic() {
        mockedStaticEm.close();
        mockedStaticDtu.close();
    }

    @Test
    void convertShouldReturnFilteredItemsWithEqualDatesInItemAndElementTest() {
        //given
        List<Item> expected = List.of(
                new Item(new Element(ModelUtils.INSTANT), DATE_TIME),
                new Item(new Element(ModelUtils.INSTANT), DATE_TIME),
                new Item(new Element(ModelUtils.INSTANT), DATE_TIME)
        );

        mockedStaticDtu.when(() -> DateTimeUtils.getInstant(any(LocalDateTime.class)))
                .thenReturn(ModelUtils.INSTANT);

        //return the same element
        mockedStaticEm.when(() -> ElementMapper.map(any(Element.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //when
        List<Item> actual = exercise4.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void convertShouldReturnEmptyListWhenTransactionIsNullTest() {
        //given
        //when
        List<Item> actual = exercise4.convert(null);

        //then
        assertEquals(List.of(), actual);
    }
}