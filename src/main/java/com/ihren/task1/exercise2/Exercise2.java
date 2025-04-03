package com.ihren.task1.exercise2;

import com.ihren.task1.exercise2.models.Element;
import com.ihren.task1.exercise2.models.Item;
import com.ihren.task1.exercise2.models.Transaction;
import com.ihren.task1.exercise2.models.CustomerCommonData;
import java.util.Optional;

public class Exercise2 {

//    public String getId(Transaction transaction) {
//        String result = null;
//        Item item = transaction.getItem();
//        if (item != null) {
//            Element element = item.getElement();
//            if (element != null && element.getId() != null) {
//                result = Optional.of(CUSTOMER_COMMON_DATA.getId().toString())
//                        .orElse(null);
//            }
//        }
//        return result;
//    }

    private CustomerCommonData customerCommonData;

    public String getId(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .map(Transaction::item)
                .map(Item::element)
                .map(Element::id)
                .map(id -> customerCommonData.id())
                .map(Object::toString)
                .orElse(null);
    }
}
