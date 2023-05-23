package io.task.template.serivces.items;

import io.task.template.CommonTest;
import io.task.template.data.repositories.items.LineRepository;
import io.task.template.services.items.ItemsManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ItemsManagementService.class})
public class ItemsManagementServiceTest extends CommonTest {

    @MockBean
    private LineRepository lineRepository;

    @Autowired
    private ItemsManagementService itemsManagementService;


    @Test
    void getItemsManagementTest() {
        final var lines = getTestLines(2);
        Mockito.when(this.lineRepository.findAll())
                .thenReturn(lines);

        final var linesSource = this.itemsManagementService.getItemsManagement();

        Assertions.assertEquals(lines, linesSource);
        verify(this.lineRepository, times(1))
                .findAll();
    }

    @Test
    void postItemsManagementTest() {

        final var lines = getTestLines(2);
        final var id = UUID.randomUUID().toString();
        lines.get(0).setId(id);

        when(this.lineRepository.saveAll(any(List.class))).thenReturn(lines);

        this.itemsManagementService.postItemsManagement(lines);

        Assertions.assertEquals(lines.get(0).getId(), id);
        verify(this.lineRepository).saveAll(lines);
    }

}
