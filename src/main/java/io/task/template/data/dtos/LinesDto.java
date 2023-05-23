package io.task.template.data.dtos;


import io.task.template.data.entities.items.Line;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LinesDto {

    private List<Line> lines;

    public static LinesDto of(final List<Line> lines) {
        return new LinesDto(lines);
    }
}
