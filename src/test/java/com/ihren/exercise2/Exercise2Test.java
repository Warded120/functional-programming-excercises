package com.ihren.exercise2;

import com.ihren.exercise2.models.CustomerCommonData;
import com.ihren.exercise2.models.Element;
import com.ihren.exercise2.models.Item;
import com.ihren.exercise2.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Exercise2Test {

    @Mock
    CustomerCommonData customerCommonData;

    @InjectMocks
    Exercise2 exercise2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getIdRefactoredShouldReturnCustomerCommonDataIdWhenEverythingIsInitializedTest() {
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
        String actual = exercise2.getId(transaction);
        //then
        assertEquals(expected, actual);
        verify(customerCommonData, times(1)).id();
    }

    @Test
    void getIdShouldReturnNullWhenElementIsNullTest() {
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
    void getIdShouldReturnNullWhenCustomerCommonDataIsNullTest() {
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
            verify(customerCommonData, times(1)).id();
    }

    @Test
    void getIdShouldReturnNullWhenTransactionIsNull() {
        assertNull(exercise2.getId(null));
    }
}