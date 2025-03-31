package com.ihren.exercise3.models;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Item {
    private final String type;
    private final Element element;
    private String transactionId;
}
