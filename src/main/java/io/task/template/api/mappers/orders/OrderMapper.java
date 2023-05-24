package io.task.template.api.mappers.orders;

import io.task.template.api.v1.resources.OrderItemResource;
import io.task.template.api.v1.resources.OrderOfferResource;
import io.task.template.api.v1.resources.OrderResource;
import io.task.template.data.entities.orders.Order;
import io.task.template.data.entities.orders.OrderItem;
import io.task.template.data.entities.orders.OrderOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderItemResource map(OrderItem orderItem);

    OrderItem map(OrderItemResource orderItemResource);

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "offer.priceReduction", target = "priceReduction")
    @Mapping(source = "offer.quantityThreshold", target = "quantityThreshold")
    OrderOfferResource map(OrderOffer orderOffer);

    @Mapping(source = "itemId", target = "item.id")
    @Mapping(source = "priceReduction", target = "offer.priceReduction")
    @Mapping(source = "quantityThreshold", target = "offer.quantityThreshold")
    OrderOffer map(OrderOfferResource orderOfferResource);

    OrderResource map(Order order);

    Order map(OrderResource orderResource);

    List<OrderResource> mapOrderResource(List<Order> orders);

    List<Order> map(List<OrderResource> ordersResource);


}
