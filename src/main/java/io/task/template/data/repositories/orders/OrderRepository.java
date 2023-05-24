package io.task.template.data.repositories.orders;

import io.task.template.data.entities.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByIdIn(List<String> ids);

}
