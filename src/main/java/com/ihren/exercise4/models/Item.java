package com.ihren.exercise4.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Item {
    private Element element;
    private LocalDateTime startDateTime;
}
