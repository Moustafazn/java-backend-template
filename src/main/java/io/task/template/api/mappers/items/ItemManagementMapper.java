package io.task.template.api.mappers.items;

import io.task.template.api.v1.resources.ItemsManagementItemResource;
import io.task.template.api.v1.resources.ItemsManagementLineResource;
import io.task.template.data.entities.items.Item;
import io.task.template.data.entities.items.Line;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemManagementMapper {

    ItemsManagementItemResource map(Item item);

    Item map(ItemsManagementItemResource itemResource);

    ItemsManagementLineResource map(Line line);

    Line map(ItemsManagementLineResource lineResource);

    List<ItemsManagementLineResource> mapLineSources(List<Line> lines);

    List<Line> mapLineList(List<ItemsManagementLineResource> lineResources);


}
