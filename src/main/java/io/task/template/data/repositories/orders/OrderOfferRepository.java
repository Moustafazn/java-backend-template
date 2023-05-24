package io.task.template.data.repositories.orders;

import io.task.template.data.entities.orders.OrderOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOfferRepository extends JpaRepository<OrderOffer, String> {
}
