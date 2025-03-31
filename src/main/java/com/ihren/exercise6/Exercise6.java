package com.ihren.exercise6;

import com.ihren.exercise6.models.*;

import java.util.Optional;

public class Exercise6 {
    private static final String SOME_TYPE = "SOME_TYPE";

//    public boolean filter(Item item) {
//        CharSequence type = item.getType();
//        Boolean exist = item.isExist();
//
//        if (type != null && type.toString().equalsIgnoreCase(SOME_TYPE)) {
//            return true;
//        } else {
//            return exist != null && exist;
//        }
//    }

    public boolean filter(Item item) {
        return  Optional.ofNullable(item)
                    .map(Item::getType)
                    .filter(type -> type.equalsIgnoreCase(SOME_TYPE))
                    .isPresent() ||
                Optional.ofNullable(item)
                    .map(Item::getIsExist)
                    .orElse(false);
    }

    public boolean filterWithoutItemNullHandling(Item item) {
        return  Optional.ofNullable(item.getType())
                .filter(type -> type.equalsIgnoreCase(SOME_TYPE))
                .isPresent() ||
                Optional.ofNullable(item.getIsExist()).orElse(false);
    }
}
