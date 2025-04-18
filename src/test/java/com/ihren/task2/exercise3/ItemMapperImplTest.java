package com.ihren.task2.exercise3;

import com.ihren.task2.exercise3.model.Amount;
import com.ihren.task2.exercise3.model.Change;
import com.ihren.task2.exercise3.model.Element;
import com.ihren.task2.exercise3.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ItemMapperImplTest {
    @Spy
    @InjectMocks
    private ItemMapperImpl itemMapper;

    @Test
    void should_ReturnItem_when_ElementIsValid() {
        // given
        String expectedDescription = "data";
        String expectedCurrency = "UAH";

        Change expectedChange = new Change(expectedCurrency, BigDecimal.valueOf(0.0));
        Item expected = new Item("", expectedDescription, expectedChange);

        Amount amount = mock(Amount.class);
        given(amount.currencyCode()).willReturn(expectedCurrency);

        Element element = mock(Element.class);
        given(element.type()).willReturn(expectedDescription);
        given(element.amount()).willReturn(amount);

        // when
        Item actual = itemMapper.map(element);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnItemList_when_ElementListIsValid() {
        // given
        Element element = mock(Element.class);
        Item item = mock(Item.class);

        List<Element> elements = List.of(element);
        List<Item> expected = List.of(item);

        given(itemMapper.map(element)).willReturn(item);

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
    void should_ReturnEmptyList_when_ElementListIsEmpty() {
        // when
        // then
        assertEquals(List.of(), itemMapper.map(List.of()));
    }
}