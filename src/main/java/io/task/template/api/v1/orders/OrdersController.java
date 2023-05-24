package io.task.template.api.v1.orders;

import io.task.template.api.mappers.orders.OrderMapper;
import io.task.template.api.v1.AbstractController;
import io.task.template.api.v1.OrdersApi;
import io.task.template.api.v1.resources.OrdersListResource;
import io.task.template.api.v1.resources.OrdersResource;
import io.task.template.services.orders.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrdersController extends AbstractController implements OrdersApi {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseEntity<OrdersListResource> getOrders(final List<String> orderIds) {
        final var orders = orderMapper.mapOrderResource(ordersService.getOrders(orderIds));
        return ResponseEntity.ok().body(new OrdersListResource().orders(orders));
    }

    @Override
    public ResponseEntity<OrdersResource> postOrders(final OrdersResource ordersResource) {
        final var orders = ordersService.postOrders(orderMapper.map(ordersResource.getOrders()));
        return ResponseEntity.ok().body(new OrdersResource().orders(orderMapper.mapOrderResource(orders)));
    }
}
