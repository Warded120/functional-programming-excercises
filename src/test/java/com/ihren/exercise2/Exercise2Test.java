package com.ihren.exercise2;

import com.ihren.exercise2.models.CustomerCommonData;
import com.ihren.exercise2.models.Element;
import com.ihren.exercise2.models.Item;
import com.ihren.exercise2.models.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class Exercise2Test {
    Exercise2 exercise2 = new Exercise2();

    @Test
    void getIdRefactoredShouldReturnCustomerCommonDataIdWhenEverythingIsInitializedTest() {
        //given
        Transaction transaction = new Transaction(
                new Item(
                        new Element("a")
                )
        );
        String expected = "1";
        Long mockLong = 1L;

        try (MockedStatic<CustomerCommonData> mockedStatic = Mockito.mockStatic(CustomerCommonData.class)) {
            mockedStatic.when(CustomerCommonData::getId).thenReturn(mockLong);
            //when
            String actual = exercise2.getId(transaction);
            //then
            assertEquals(expected, actual);
            mockedStatic.verify(CustomerCommonData::getId);
        }
    }

    @Test
    void getIdShouldReturnNullWhenElementIsNullTest() {
        //given
        Transaction transaction = new Transaction(
                new Item(
                        null
                )
        );

        //when
        String actual = exercise2.getId(transaction);

        //then
        System.out.println(actual);
        assertNull(actual);
    }
}