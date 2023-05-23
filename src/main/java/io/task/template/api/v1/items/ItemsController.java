package io.task.template.api.v1.items;


import io.task.template.api.mappers.items.LineMapper;
import io.task.template.api.v1.AbstractController;
import io.task.template.api.v1.ItemsApi;
import io.task.template.api.v1.resources.LinesResource;
import io.task.template.services.items.ItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemsController extends AbstractController implements ItemsApi {

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private LineMapper lineMapper;

    @Override
    public ResponseEntity<LinesResource> getInventory() {
        final var lines = itemsService.getInventory();
        return ResponseEntity.ok().body(lineMapper.map(lines));
    }

}
