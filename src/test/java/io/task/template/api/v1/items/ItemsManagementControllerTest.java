package io.task.template.api.v1.items;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.task.template.CommonTest;
import io.task.template.api.v1.resources.ItemIdsResource;
import io.task.template.api.v1.resources.ItemsManagementLineListResource;
import io.task.template.api.v1.resources.ItemsManagementLinesResource;
import io.task.template.services.items.ItemsManagementService;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemsManagementControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemsManagementService itemsManagementService;


    @Test
    void canGetItemsManagement() throws Exception {

        final var lines = getTestLines(2);

        Mockito.when(this.itemsManagementService.getItemsManagement()).thenReturn(lines);

        /* Building the request */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/items-management")
                .accept(MediaType.APPLICATION_JSON);

        /* Performing the request */
        var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(response, ItemsManagementLineListResource.class);
        Assertions.assertNotNull(result);
        assertEquals(2, result.getLines().size());
        Assertions.assertEquals(lines.get(0).getQuantity(), result.getLines().get(0).getQuantity());
        Assertions.assertEquals(lines.get(1).getItem().getPrice(), result.getLines().get(1).getItem().getPrice());
        Assertions.assertEquals(lines.get(1).getItem().getCost(), result.getLines().get(1).getItem().getCost());

    }

    @Test
    void postItemsManagementTest() throws Exception {

        final var lines = getTestLines(2);
        lines.get(0).setId(String.valueOf(UUID.randomUUID()));

        final var itemsManagementLines = getTestItemsManagementLineResource(2);
        Mockito.when(this.itemsManagementService.postItemsManagement(isA(List.class))).thenReturn(lines);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/items-management")
                .content(this.objectMapper.writeValueAsString(new ItemsManagementLinesResource().lines(itemsManagementLines)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(response, ItemsManagementLinesResource.class);
        Assertions.assertNotNull(result);
        assertEquals(2, result.getLines().size());
        Assertions.assertEquals(lines.get(0).getQuantity(), result.getLines().get(0).getQuantity());
        Assertions.assertEquals(lines.get(1).getItem().getCost(), result.getLines().get(1).getItem().getCost());

    }

    @Test
    void deleteInventoryManagementItemsTest() throws Exception {
        final var ids = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        /* Building the request */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.
                delete(String.format("/api/v1/items-management"))
                .content(this.objectMapper.writeValueAsString(new ItemIdsResource().itemIds(ids)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        /* Performing the request */
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(this.itemsManagementService).deleteInventoryManagementItems(ids);
    }


}
