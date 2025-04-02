package com.ihren.exercise2;

import com.ihren.exercise2.models.CustomerCommonData;
import com.ihren.exercise2.models.Element;
import com.ihren.exercise2.models.Item;
import com.ihren.exercise2.models.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Exercise2Test {

    @Mock
    private CustomerCommonData customerCommonData;

    @InjectMocks
    private Exercise2 exercise2;

    @Test
    @DisplayName("Should return CustomerCommonData id when everything is initialized")
    void getIdReturnsCustomerCommonDataIdWhenInitialized() {
        //given
        Transaction transaction = new Transaction(
                "something",
                new Item(
                        "something",
                        new Element("a")
                )
        );
        String expected = "1";
        Long mockLong = 1L;

        when(customerCommonData.id()).thenReturn(mockLong);

        //when
        String actual = exercise2.getId(transaction);
        //then
        assertEquals(expected, actual);
        verify(customerCommonData).id();
    }

    @Test
    @DisplayName("Should return null when transaction is null")
    void getIdReturnsNullWhenTransactionIsNull() {
        //when
        assertNull(exercise2.getId(null));
    }

    @Test
    @DisplayName("Should return null when item is null")
    void getIdReturnsNullWhenItemIsNull() {
        //given
        Transaction transaction = new Transaction("something", null);

        //when
        //then
        assertNull(exercise2.getId(transaction));
    }

    @Test
    @DisplayName("Should return null when element is null")
    void getIdReturnsNullWhenElementIsNull() {
        //given
        Transaction transaction = new Transaction(
                "something",
                new Item(
                        "something",
                        null
                )
        );

        //when
        String actual = exercise2.getId(transaction);

        //then
        assertNull(actual);
    }

    @Test
    @DisplayName("Should return null when element id is null")
    void getIdReturnsNullWhenElementIdIsNull() {
        //given
        Transaction transaction = new Transaction(
                "something",
                new Item(
                        "something",
                        new Element(null)
                )
        );

        //when
        String actual = exercise2.getId(transaction);

        //then
        assertNull(actual);
    }

    @Test
    @DisplayName("Should return null when CustomerCommonData is null")
    void getIdReturnsNullWhenCustomerCommonDataIsNull() {
        //given
        Transaction transaction = new Transaction(
                "something",
                new Item(
                        "something",
                        new Element("a")
                )
        );

        when(customerCommonData.id()).thenReturn(null);

        //when
        String actual = exercise2.getId(transaction);

        //then
        assertNull(actual);
        verify(customerCommonData).id();
    }
}