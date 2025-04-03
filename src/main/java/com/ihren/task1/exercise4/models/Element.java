package com.ihren.task1.exercise4.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class Element {
    private Instant startDateTime;
}
