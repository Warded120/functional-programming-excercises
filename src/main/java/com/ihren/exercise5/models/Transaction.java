package com.ihren.exercise5.models;

import java.util.List;

public record Transaction (
    List<Item> items
) {}
