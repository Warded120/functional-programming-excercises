package com.ihren.exercise4.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Item {
    Element element;
    LocalDateTime startDateTime;
}
