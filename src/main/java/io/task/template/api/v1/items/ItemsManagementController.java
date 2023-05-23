package io.task.template.api.v1.items;


import io.task.template.api.mappers.items.ItemManagementMapper;
import io.task.template.api.v1.AbstractController;
import io.task.template.api.v1.ItemsManagementApi;
import io.task.template.api.v1.resources.DeletedItemIdsResource;
import io.task.template.api.v1.resources.ItemIdsResource;
import io.task.template.api.v1.resources.ItemsManagementLineListResource;
import io.task.template.api.v1.resources.ItemsManagementLinesResource;
import io.task.template.services.items.ItemsManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemsManagementController extends AbstractController implements ItemsManagementApi {

    @Autowired
    private ItemsManagementService itemsManagementService;

    @Autowired
    private ItemManagementMapper itemManagementMapper;

    @Override
    public ResponseEntity<DeletedItemIdsResource> deleteInventoryManagementItems(final ItemIdsResource itemIdsResource) {
        final var response = itemsManagementService.deleteInventoryManagementItems(itemIdsResource.getItemIds());
        return ResponseEntity.ok().body(new DeletedItemIdsResource().itemIds(response));
    }

    @Override
    public ResponseEntity<ItemsManagementLineListResource> getItemsManagement() {
        final var lineSourceList = itemManagementMapper.mapLineSources(itemsManagementService.getItemsManagement());
        return ResponseEntity.ok().body(new ItemsManagementLineListResource().lines(lineSourceList));
    }

    @Override
    public ResponseEntity<ItemsManagementLinesResource> postItemsManagement(final ItemsManagementLinesResource itemsManagementLinesResource) {
        final var lines = itemsManagementService.postItemsManagement(itemManagementMapper.mapLineList(itemsManagementLinesResource.getLines()));
        return ResponseEntity.ok().body(new ItemsManagementLinesResource().lines(itemManagementMapper.mapLineSources(lines)));
    }

}
