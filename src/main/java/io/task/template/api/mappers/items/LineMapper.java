package io.task.template.api.mappers.items;

import io.task.template.api.v1.resources.ItemResource;
import io.task.template.api.v1.resources.LineResource;
import io.task.template.api.v1.resources.LinesResource;
import io.task.template.data.dtos.LinesDto;
import io.task.template.data.entities.items.Item;
import io.task.template.data.entities.items.Line;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LineMapper {

    ItemResource map(Item item);

    LineResource map(Line line);

    LinesResource map(LinesDto linesDto);
}
