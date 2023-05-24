package io.task.template.serivces.orders;

import io.task.template.CommonTest;
import io.task.template.data.repositories.orders.OrderRepository;
import io.task.template.services.orders.OrdersService;
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
@ContextConfiguration(classes = {OrdersService.class})
public class OrdersServiceTest extends CommonTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrdersService ordersService;


    @Test
    void getOrdersTest() {
        final var orders = this.getTestOrders(2);
        final var ids = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        orders.get(0).setId(ids.get(0));
        orders.get(1).setId(ids.get(1));
        Mockito.when(this.orderRepository.findByIdIn(ids))
                .thenReturn(orders);

        final var returnedOrders = this.ordersService.getOrders(ids);

        Assertions.assertEquals(orders, returnedOrders);
        verify(this.orderRepository, times(1))
                .findByIdIn(ids);
    }

    @Test
    void postOrdersTest() {
        final var id = UUID.randomUUID().toString();
        final var orders = this.getTestOrders(2);
        orders.get(0).setId(id);

        when(this.orderRepository.saveAll(any(List.class))).thenReturn(orders);

        this.ordersService.postOrders(orders);

        Assertions.assertEquals(orders.get(0).getId(), id);
        verify(this.orderRepository).saveAll(orders);
    }

}
