package com.ihren.exercise7.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Item {
    private Long id;
    private Long parentId;
    private Boolean isCancelled;
    private Action action;
    private String returnReason;
}
