package io.task.template.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.task.template.CommonTest;
import io.task.template.api.v1.resources.LinesResource;
import io.task.template.services.items.ItemsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemsControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemsService itemsService;


    @Test
    void canGetInventory() throws Exception {

        final var linesDto = getTestLinesDto(2);

        Mockito.when(this.itemsService.getInventory()).thenReturn(linesDto);

        /* Building the request */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/items")
                .accept(MediaType.APPLICATION_JSON);

        /* Performing the request */
        var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(response, LinesResource.class);
        Assertions.assertNotNull(result);
        assertEquals(2, result.getLines().size());
        Assertions.assertEquals(linesDto.getLines().get(0).getQuantity(), result.getLines().get(0).getQuantity());
        Assertions.assertEquals(linesDto.getLines().get(1).getItem().getPrice(), result.getLines().get(1).getItem().getPrice());
    }


}
