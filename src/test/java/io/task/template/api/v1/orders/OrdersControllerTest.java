package io.task.template.api.v1.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.task.template.CommonTest;
import io.task.template.api.v1.resources.OrdersListResource;
import io.task.template.api.v1.resources.OrdersResource;
import io.task.template.services.orders.OrdersService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrdersControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrdersService ordersService;


    @Test
    void canGetOrders() throws Exception {

        final var orders = this.getTestOrders(2);

        Mockito.when(this.ordersService.getOrders((isA(List.class)))).thenReturn(orders);

        /* Building the request */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/orders")
                .queryParam("orderIds", this.objectMapper.writeValueAsString(ids))
                .accept(MediaType.APPLICATION_JSON);

        /* Performing the request */
        var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(response, OrdersListResource.class);
        Assertions.assertNotNull(result);
        assertEquals(2, result.getOrders().size());
        Assertions.assertEquals(orders.get(0).getItems().get(0).getName(), result.getOrders().get(0).getItems().get(0).getName());
        Assertions.assertEquals(orders.get(1).getItems().get(0).getDiscountedPrice(), result.getOrders().get(1).getItems().get(0).getDiscountedPrice());

    }

    @Test
    void postOrdersTest() throws Exception {

        final var orders = this.getTestOrders(2);

        final var ordersResource = this.getTestOrdersResource(2);
        Mockito.when(this.ordersService.postOrders(isA(List.class))).thenReturn(orders);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/orders")
                .content(this.objectMapper.writeValueAsString(ordersResource))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(response, OrdersResource.class);
        Assertions.assertNotNull(result);
        assertEquals(2, result.getOrders().size());
        Assertions.assertEquals(orders.get(0).getItems().get(0).getName(), result.getOrders().get(0).getItems().get(0).getName());
        Assertions.assertEquals(orders.get(1).getItems().get(0).getDiscountedPrice(), result.getOrders().get(1).getItems().get(0).getDiscountedPrice());
    }


}
