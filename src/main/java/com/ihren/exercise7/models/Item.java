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
    Long id;
    Long parentId;
    Boolean isCancelled;
    Action action;
    String returnReason;
}
