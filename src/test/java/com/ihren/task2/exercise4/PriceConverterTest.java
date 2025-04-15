package com.ihren.task2.exercise4;

import com.ihren.task2.exercise4.model.MonetaryAmount;
import com.ihren.task2.exercise4.model.PriceModifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PriceConverterTest {

    @Spy
    private PriceConverter priceConverter;

    @Test
    void should_ReturnPriceModifier_when_InputIsValid() {
        //given
        PriceModifier priceModifier = mock(PriceModifier.class);
        PriceModifier newPriceModifier = mock(PriceModifier.class);
        MonetaryAmount monetaryAmount = mock(MonetaryAmount.class);

        willReturn(newPriceModifier).given(priceConverter).map(priceModifier, monetaryAmount);
        willDoNothing().given(priceConverter).populate(newPriceModifier, priceModifier);

        //when
        PriceModifier actual = priceConverter.convert(priceModifier, monetaryAmount);

        //then
        assertEquals(newPriceModifier, actual);
        then(priceConverter).should().map(priceModifier, monetaryAmount);
        then(priceConverter).should().populate(newPriceModifier, priceModifier);
    }
}