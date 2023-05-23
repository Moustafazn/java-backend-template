package io.task.template.serivces;

import io.task.template.CommonTest;
import io.task.template.data.repositories.items.LineRepository;
import io.task.template.services.items.ItemsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ItemsService.class})
public class ItemsServiceTest extends CommonTest {

    @MockBean
    private LineRepository lineRepository;

    @Autowired
    private ItemsService itemsService;


    @Test
    void getLinesTest() {
        final var lines = getTestLines(2);
        Mockito.when(this.lineRepository.findAll())
                .thenReturn(lines);

        final var linesDto = this.itemsService.getInventory();

        Assertions.assertEquals(lines, linesDto.getLines());
        verify(this.lineRepository, times(1))
                .findAll();
    }

}
