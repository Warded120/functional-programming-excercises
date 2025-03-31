package com.ihren.exercise4.models;

import java.util.List;

public record Transaction (
    List<Item> items
) {}
