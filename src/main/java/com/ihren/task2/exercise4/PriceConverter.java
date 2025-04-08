package com.ihren.task2.exercise4;

import com.ihren.task2.exercise4.model.MonetaryAmount;
import com.ihren.task2.exercise4.model.PriceModifier;

public class PriceConverter {
    public PriceModifier convert(PriceModifier priceModifier, MonetaryAmount monetaryAmount) {
        var newPriceModifier = map(priceModifier, monetaryAmount);
        populate(newPriceModifier, priceModifier);
        return newPriceModifier;
    }

    protected PriceModifier map(PriceModifier priceModifier, MonetaryAmount monetaryAmount) {
        throw new UnsupportedOperationException("Should not be called without mocking");
    }

    protected void populate(Object newPriceModifier, PriceModifier priceModifier) {
        throw new UnsupportedOperationException("Should not be called without mocking");
    }
}
