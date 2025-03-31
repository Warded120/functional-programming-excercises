package com.ihren.exercise4;

import com.ihren.exercise4.models.DateTimeUtils;
import com.ihren.exercise4.models.Element;
import com.ihren.exercise4.models.ElementMapper;
import com.ihren.exercise4.models.Item;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.time.LocalDateTime;
import java.util.List;

import static com.ihren.exercise4.ModelUtils.dateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Exercise4Test {

    Exercise4 exercise4 = new Exercise4();

    @Test
    void convertShouldReturnFilteredItemsWithEqualDatesInItemAndElementTest() {
        //given
        List<Item> expected = List.of(
                new Item(new Element(ModelUtils.instant), dateTime),
                new Item(new Element(ModelUtils.instant), dateTime),
                new Item(new Element(ModelUtils.instant), dateTime)
        );

        try(MockedStatic<DateTimeUtils> mockedStaticDtu = mockStatic(DateTimeUtils.class);
            MockedStatic<ElementMapper> mockedStaticEm = mockStatic(ElementMapper.class)) {

            mockedStaticDtu.when(() -> DateTimeUtils.getInstant(any(LocalDateTime.class)))
                    .thenReturn(ModelUtils.instant);
            mockedStaticEm.when(() -> ElementMapper.map(any(Element.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

            //when
            List<Item> actual = exercise4.convert(ModelUtils.TRANSACTION);

            //then
            assertEquals(expected, actual);
        }
    }


}