package com.ihren.task2.exercise3;

import com.ihren.task2.exercise3.model.Element;
import com.ihren.task2.exercise3.model.Item;
import io.vavr.control.Try;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ItemMapperImpl implements ItemMapper {
    @Override
    public List<Item> map(List<Element> elements) {
        return Stream.ofNullable(elements)
                .flatMap(List::stream)
                .map(this::map)
                .toList();
    }

    @Override
    public Item map(Element element) {
        return Try.of(() -> {
            LocalDateTime startDate = element.startDate().atZone(ZoneId.systemDefault()).toLocalDateTime();

            Long duration =
                Optional.of(Duration.between(element.startDate(), element.endDate()))
                    .filter(dur -> !dur.isNegative())
                    .map(Duration::toDays)
                    .orElseThrow(IllegalArgumentException::new);

            String data = Optional.ofNullable(element.data())
                    .orElseThrow(IllegalArgumentException::new);

            Double award = Double.parseDouble(element.award());

            return new Item(startDate, duration, data, award);
        })
        .getOrElseGet(ex -> null);
    }
}
