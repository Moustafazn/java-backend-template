package io.task.template.services.items;


import io.task.template.data.entities.items.Line;
import io.task.template.data.repositories.items.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsManagementService {

    @Autowired
    private LineRepository lineRepository;


    @Transactional
    public List<String> deleteInventoryManagementItems(List<String> itemIds) {
        lineRepository.deleteByIdIn(itemIds);
        return itemIds;
    }

    @Transactional(readOnly = true)
    public List<Line> getItemsManagement() {
        return lineRepository.findAll();
    }

    @Transactional
    public List<Line> postItemsManagement(List<Line> lines) {
        return lineRepository.saveAll(lines);
    }


}
