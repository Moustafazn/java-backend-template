package io.task.template.services.orders;

import io.task.template.data.entities.orders.Order;
import io.task.template.data.repositories.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> getOrders(final List<String> orderIds) {
        return orderRepository.findByIdIn(orderIds);
    }

    @Transactional
    public List<Order> postOrders(final List<Order> orders) {
        return orderRepository.saveAll(orders);
    }
}
