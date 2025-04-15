package com.ihren.task2.exercise3;

import com.ihren.task2.exercise3.model.Element;
import com.ihren.task2.exercise3.model.Item;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ItemMapperImplTest {
    @InjectMocks
    private ItemMapperImpl itemMapper;

    @Nested
    class MapElementTests {
        @Test
        void should_ReturnItem_when_ElementIsValid() {
            // given
            String expectedDescription = "data";
            String expectedCurrency = "UAH";

            Item.Change expectedChange = new Item.Change(expectedCurrency, BigDecimal.valueOf(0.0));
            Item expected = new Item("", expectedDescription, expectedChange);

            Element.Amount amountMock = mock(Element.Amount.class);
            given(amountMock.currencyCode()).willReturn(expectedCurrency);

            Element elementMock = mock(Element.class);
            given(elementMock.type()).willReturn(expectedDescription);
            given(elementMock.amount()).willReturn(amountMock);

            // when
            Item actual = itemMapper.map(elementMock);

            // then
            assertEquals(expected, actual);
            then(elementMock).should().type();
            then(elementMock).should().amount();
            then(amountMock).should().currencyCode();
        }

        @Test
        void should_ReturnNull_when_ElementFieldsIsNull() {
            // given
            String description = "description";

            Element elementMock = mock(Element.class);
            given(elementMock.type()).willReturn(description);
            given(elementMock.amount()).willReturn(null);

            Item expected = new Item("", description, null);

            // when
            Item actual = itemMapper.map(elementMock);

            // then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class MapElementListTests {
        @Test
        void should_ReturnItemList_when_ElementListIsValid() {
            // given
            String currency = "UAH";

            Element.Amount amount1 = mock(Element.Amount.class);
            Element.Amount amount2 = mock(Element.Amount.class);
            Element.Amount amount3 = mock(Element.Amount.class);

            given(amount1.currencyCode()).willReturn(currency);
            given(amount2.currencyCode()).willReturn(currency);
            given(amount3.currencyCode()).willReturn(currency);

            Element e1 = mock(Element.class);
            Element e2 = mock(Element.class);
            Element e3 = mock(Element.class);

            given(e1.type()).willReturn("data1");
            given(e2.type()).willReturn("data2");
            given(e3.type()).willReturn("data3");

            given(e1.amount()).willReturn(amount1);
            given(e2.amount()).willReturn(amount2);
            given(e3.amount()).willReturn(amount3);

            List<Element> elements = List.of(e1, e2, e3);

            List<Item> expected = List.of(
                    new Item("", "data1", new Item.Change(currency, BigDecimal.valueOf(0.0))),
                    new Item("", "data2", new Item.Change(currency, BigDecimal.valueOf(0.0))),
                    new Item("", "data3", new Item.Change(currency, BigDecimal.valueOf(0.0)))
            );

            // when
            List<Item> actual = itemMapper.map(elements);

            // then
            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnNull_when_ElementListIsNull() {
            // when
            // then
            assertNull(itemMapper.map((List<Element>) null));
        }

        @Test
        void should_ReturnNull_when_ElementListIsEmpty() {
            // when
            // then
            assertEquals(List.of(), itemMapper.map(List.of()));
        }
    }
}