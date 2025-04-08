package com.ihren.task2.exercise3;

import com.ihren.task2.exercise3.model.Element;
import com.ihren.task2.exercise3.model.Item;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ItemMapperImplTest {
    @InjectMocks
    ItemMapperImpl itemMapper;

    @Nested
    class MapElementTests {
        @Test
        void should_ReturnItem_when_ElementIsValid() {
            //given
            Instant instant = Instant.now();
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

            Item expected = new Item(localDateTime, 1L, "data", 360d);

            Element elementMock = mock(Element.class);
            given(elementMock.startDate()).willReturn(instant);
            given(elementMock.endDate()).willReturn(instant.plusSeconds(3600*24));
            given(elementMock.data()).willReturn("data");
            given(elementMock.award()).willReturn("360");

            //when
            Item actual = itemMapper.map(elementMock);

            //then
            assertEquals(expected, actual);
            then(elementMock).should(times(2)).startDate();
            then(elementMock).should().endDate();
            then(elementMock).should().data();
            then(elementMock).should().award();
        }

        @Test
        void should_ReturnNull_when_ElementStartDateIsNull() {
            //given
            Element elementMock = mock(Element.class);
            given(elementMock.startDate()).willReturn(null);

            //when
            Item actual = itemMapper.map(elementMock);

            //then
            assertNull(actual);
            then(elementMock).should().startDate();
        }

        @Test
        void should_ReturnNull_when_ElementEndDateIsNull() {
            //given
            Instant instant = Instant.now();

            Element elementMock = mock(Element.class);
            given(elementMock.startDate()).willReturn(instant);
            given(elementMock.endDate()).willReturn(null);

            //when
            Item actual = itemMapper.map(elementMock);

            //then
            assertNull(actual);
            then(elementMock).should(times(2)).startDate();
            then(elementMock).should().endDate();
        }

        @Test
        void should_ReturnNull_when_ElementStartDateIsAfterEndDate() {
            //given
            Instant instant = Instant.now();

            Element elementMock = mock(Element.class);
            given(elementMock.startDate()).willReturn(instant);
            given(elementMock.endDate()).willReturn(instant.minusSeconds(3600*24));

            //when
            Item actual = itemMapper.map(elementMock);

            //then
            assertNull(actual);
            then(elementMock).should(times(2)).startDate();
            then(elementMock).should().endDate();
        }

        @Test
        void should_ReturnNull_when_ElementDataIsNull() {
            //given
            Instant instant = Instant.now();

            Element elementMock = mock(Element.class);
            given(elementMock.startDate()).willReturn(instant);
            given(elementMock.endDate()).willReturn(instant.plusSeconds(3600*24));
            given(elementMock.data()).willReturn(null);

            //when
            Item actual = itemMapper.map(elementMock);

            //then
            assertNull(actual);
            then(elementMock).should(times(2)).startDate();
            then(elementMock).should().endDate();
            then(elementMock).should().data();
        }

        @Test
        void should_ReturnNull_when_ElementAwardIsInvalid() {
            //given
            Instant instant = Instant.now();

            Element elementMock = mock(Element.class);
            given(elementMock.startDate()).willReturn(instant);
            given(elementMock.endDate()).willReturn(instant.plusSeconds(3600*24));
            given(elementMock.data()).willReturn("data");
            given(elementMock.award()).willReturn("threeHundredSixty");

            //when
            Item actual = itemMapper.map(elementMock);

            //then
            assertNull(actual);
            then(elementMock).should(times(2)).startDate();
            then(elementMock).should().endDate();
            then(elementMock).should().data();
            then(elementMock).should().award();
        }

        @Test
        void should_ReturnNull_when_ElementAwardIsNull() {
            //given
            Instant instant = Instant.now();

            Element elementMock = mock(Element.class);
            given(elementMock.startDate()).willReturn(instant);
            given(elementMock.endDate()).willReturn(instant.plusSeconds(3600*24));
            given(elementMock.data()).willReturn("data");
            given(elementMock.award()).willReturn(null);

            //when
            Item actual = itemMapper.map(elementMock);

            //then
            assertNull(actual);
            then(elementMock).should(times(2)).startDate();
            then(elementMock).should().endDate();
            then(elementMock).should().data();
            then(elementMock).should().award();
        }
    }

    @Nested
    class MapElementListTests {
        @Test
        void should_ReturnList_when_InputIsValid() {
            //given
            Instant instant = Instant.now();
            LocalDateTime startDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            List<Element> elements = List.of(
                    new Element(instant, instant.plusSeconds(3600*24), "data1", "100.0"),
                    new Element(instant, instant.plusSeconds(3600*24*2), "data2", "200.5"),
                    new Element(instant, instant.plusSeconds(3600*24*3), "data3", "300.25")
            );

            List<Item> expected = List.of(
                    new Item(startDate, 1L, "data1", 100.0),
                    new Item(startDate, 2L, "data2", 200.5),
                    new Item(startDate, 3L, "data3", 300.25)
            );

            //when
            List<Item> actual = itemMapper.map(elements);

            //then
            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnNull_when_ElementsListIsNull() {
            assertTrue(itemMapper.map((List<Element>) null).isEmpty());
        }

        @Test
        void should_ReturnList_when_SomeListElementsIsInvalid() {
            //given
            Instant instant = Instant.now();
            LocalDateTime startDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            List<Element> elements = List.of(
                    new Element(null, instant.plusSeconds(3600*24), "data1", "100.0"),
                    new Element(instant, instant.plusSeconds(3600*24), "data1", "100.0"),
                    new Element(instant, instant.plusSeconds(3600*24*2), "data2", "200.5"),
                    new Element(instant, instant.plusSeconds(3600*24*2), "data2", "invalid"),
                    new Element(instant, instant.plusSeconds(3600*24*3), "data3", "300.25")
            );

            List<Item> expected = new ArrayList<>();
                    expected.add(null);
                    expected.add(new Item(startDate, 1L, "data1", 100.0));
                    expected.add(new Item(startDate, 2L, "data2", 200.5));
                    expected.add(null);
                    expected.add(new Item(startDate, 3L, "data3", 300.25));

            //when
            List<Item> actual = itemMapper.map(elements);

            //then
            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnList_when_AllListElementsIsInvalid() {
            //given
            Instant instant = Instant.now();

            List<Element> elements = List.of(
                    new Element(null, instant.plusSeconds(3600*24), "data1", "100.0"),
                    new Element(instant, instant.minusSeconds(3600*24), "data2", "100.0"),
                    new Element(instant, instant.plusSeconds(3600*24*2), null, "200.0"),
                    new Element(instant, instant.plusSeconds(3600*24*2), "data2", "invalid")
            );

            List<Item> expected = new ArrayList<>();
            expected.add(null);
            expected.add(null);
            expected.add(null);
            expected.add(null);

            //when
            List<Item> actual = itemMapper.map(elements);

            //then
            assertEquals(expected, actual);
        }
    }
}