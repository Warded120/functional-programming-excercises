package com.ihren.task2.exercise3;

import com.ihren.task2.exercise3.constant.StringUtils;
import com.ihren.task2.exercise3.model.Element;
import com.ihren.task2.exercise3.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemMapper {

    @Named("mapItems")
    List<Item> map(List<Element> elements);

    @Mapping(target = "typeId", constant = StringUtils.EMPTY)
    @Mapping(target = "description", source = "type")
    @Mapping(target = "change.amount", expression = "java(java.math.BigDecimal.valueOf(0.0))")
    @Mapping(target = "change.currencyCode", source = "amount.currencyCode")
    Item map(Element element);

}