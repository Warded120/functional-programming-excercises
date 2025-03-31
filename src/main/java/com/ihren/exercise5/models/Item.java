package com.ihren.exercise5.models;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Item {
    private Sale sale;
    private String fuelSale;
    private Return aReturn;
    private Data data;
}
