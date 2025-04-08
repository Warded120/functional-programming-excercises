package com.ihren.task2.exercise3;

import com.ihren.task2.exercise3.model.Element;
import com.ihren.task2.exercise3.model.Item;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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

            Element element1 = mock(Element.class);
            given(element1.startDate()).willReturn(instant);
            given(element1.endDate()).willReturn(instant.plusSeconds(3600*24));
            given(element1.data()).willReturn("data1");
            given(element1.award()).willReturn("100.0");

            Element element2 = mock(Element.class);
            given(element2.startDate()).willReturn(instant);
            given(element2.endDate()).willReturn(instant.plusSeconds(3600*24*2));
            given(element2.data()).willReturn("data2");
            given(element2.award()).willReturn("200.5");

            Element element3 = mock(Element.class);
            given(element3.startDate()).willReturn(instant);
            given(element3.endDate()).willReturn(instant.plusSeconds(3600*24*3));
            given(element3.data()).willReturn("data3");
            given(element3.award()).willReturn("300.25");

            List<Element> elements = List.of(element1, element2, element3);

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

            Element element1 = mock(Element.class);
            given(element1.startDate()).willReturn(null);

            Element element2 = mock(Element.class);
            given(element2.startDate()).willReturn(instant);
            given(element2.endDate()).willReturn(instant.plusSeconds(3600*24*2));
            given(element2.data()).willReturn("data2");
            given(element2.award()).willReturn("200.5");

            Element element3 = mock(Element.class);
            given(element3.startDate()).willReturn(instant);
            given(element3.endDate()).willReturn(instant.plusSeconds(3600*24*3));
            given(element3.data()).willReturn("data3");
            given(element3.award()).willReturn("invalid");

            Element element4 = mock(Element.class);
            given(element4.startDate()).willReturn(instant);
            given(element4.endDate()).willReturn(instant.plusSeconds(3600*24*4));
            given(element4.data()).willReturn("data4");
            given(element4.award()).willReturn("300.25");

            List<Element> elements = List.of(element1, element2, element3, element4);

            List<Item> expected = new ArrayList<>();
                    expected.add(null);
                    expected.add(new Item(startDate, 2L, "data2", 200.5));
                    expected.add(null);
                    expected.add(new Item(startDate, 4L, "data4", 300.25));

            //when
            List<Item> actual = itemMapper.map(elements);

            //then
            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnList_when_AllListElementsIsInvalid() {
            //given
            Instant instant = Instant.now();

            Element element1 = mock(Element.class);
            given(element1.startDate()).willReturn(null);

            Element element2 = mock(Element.class);
            given(element2.startDate()).willReturn(instant);
            given(element2.endDate()).willReturn(instant.minusSeconds(3600*24*2));

            Element element3 = mock(Element.class);
            given(element3.startDate()).willReturn(instant);
            given(element3.endDate()).willReturn(instant.plusSeconds(3600*24*3));
            given(element3.data()).willReturn(null);

            Element element4 = mock(Element.class);
            given(element4.startDate()).willReturn(instant);
            given(element4.endDate()).willReturn(instant.plusSeconds(3600*24*4));
            given(element4.data()).willReturn("data4");
            given(element4.award()).willReturn("invalid");

            List<Element> elements = List.of(element1, element2, element3, element4);

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