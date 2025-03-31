package com.ihren.exercise4.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Transaction {
    List<Item> items;
}
