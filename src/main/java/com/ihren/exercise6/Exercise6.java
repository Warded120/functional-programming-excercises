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
                    .map(Item::type)
                    .map(type -> type.equalsIgnoreCase(SOME_TYPE))
                    .or(() ->
                            Optional.ofNullable(item)
                            .map(Item::isExist)
                    )
                    .orElse(false);
    }

    // second variant of method, where item parameter is not null safe (as in base method)
    public boolean filterWithoutItemNullHandling(Item item) {
        return  Optional.ofNullable(item.type())
                .filter(type -> type.equalsIgnoreCase(SOME_TYPE))
                .isPresent() ||
                Optional.ofNullable(item.isExist()).orElse(false);
    }
}
