package io.task.template.services.items;

import io.task.template.data.dtos.LinesDto;
import io.task.template.data.repositories.items.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ItemsService {


    @Autowired
    private LineRepository lineRepository;

    @Transactional(readOnly = true)
    public LinesDto getInventory() {
        final var lines = this.lineRepository.findAll();
        return LinesDto.of(lines);

    }
}
