package io.task.template;

import io.task.template.api.v1.resources.*;
import io.task.template.data.dtos.LinesDto;
import io.task.template.data.entities.items.Item;
import io.task.template.data.entities.items.Line;
import io.task.template.data.entities.offers.Offer;
import io.task.template.data.entities.orders.Order;
import io.task.template.data.entities.orders.OrderItem;
import io.task.template.data.entities.orders.OrderOffer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommonTest {

    protected static final List<String> ids = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());


    protected LinesDto getTestLinesDto(final int count) {
        return LinesDto.of(getTestLines(count));
    }

    protected List<Line> getTestLines(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(i -> new Line(i, this.getTestItem(i)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected Offer getTestOffer(final int index) {
        final var offer = new Offer("offer" + index, "offer description" + index, this.getTestItem(index), new BigDecimal(index + 1.2), index);
        offer.setId(ids.get(index));
        return offer;
    }

    protected List<Offer> getTestOffers(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(this::getTestOffer)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected Item getTestItem(final int index) {
        final var item = new Item("item" + index, "Item description" + index, new BigDecimal(index + 1.2), new BigDecimal(index + 1.7));
        item.setId(ids.get(index));
        return item;
    }

    protected Order getTestOrder(final int index) {
        final var order = new Order();
        order.setId(ids.get(index));
        final var orderItem = new OrderItem("item" + index, new BigDecimal(index + 1.2), new BigDecimal(index + 1.7), order);
        final var orderOffer = new OrderOffer("offer" + index, getTestItem(index), getTestOffer(index), order);
        order.setItems(List.of(orderItem));
        order.setOffers(List.of(orderOffer));
        return order;
    }

    protected List<Order> getTestOrders(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(this::getTestOrder)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected List<ItemsManagementLineResource> getTestItemsManagementLineResource(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(i -> new ItemsManagementLineResource().quantity(i).item(this.getTestItemsManagementItemResource(i)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected ItemsManagementItemResource getTestItemsManagementItemResource(final int index) {
        return new ItemsManagementItemResource().name("item" + index).
                description("Item description" + index).
                price(new BigDecimal(index + 1.2)).id(String.valueOf(index))
                .cost(new BigDecimal(index + 13));
    }


    protected List<OrderResource> getTestOrderResource(final int index) {
        final var offerResource = getTestOffer(index);

        final OrderItemResource orderItemSource = this.getOrderItemResource(index);
        final OrderOfferResource orderOfferSource = this.getOrderOfferResource(index, offerResource);

        return List.of(new OrderResource().id(String.valueOf(index))
                .items(List.of(orderItemSource))
                .offers(List.of(orderOfferSource)));
    }


    protected OrdersResource getTestOrdersResource(final int count) {
        List<OrderResource> orders = IntStream.range(0, count)
                .mapToObj(this::getTestOrderResource)
                .flatMap(Collection::stream).collect(Collectors.toList());
        return new OrdersResource().orders(orders);
    }

    protected OrderItemResource getOrderItemResource(int index) {
        final var orderItemSource = new OrderItemResource()
                .id(String.valueOf(index))
                .name("item" + index)
                .originalPrice(new BigDecimal(index + 1.2))
                .discountedPrice(new BigDecimal(index + 1.7));
        return orderItemSource;
    }

    protected OrderOfferResource getOrderOfferResource(int index, Offer offerResource) {
        final var orderOfferSource = new OrderOfferResource().id(String.valueOf(index))
                .name("offer" + index)
                .itemId(String.valueOf(index))
                .priceReduction(offerResource.getPriceReduction()).
                quantityThreshold(offerResource.getQuantityThreshold());
        return orderOfferSource;
    }

    protected List<OffersManagementOfferResource> getTestOffersManagementOfferResource(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(i -> new OffersManagementOfferResource().name("offer" + i)
                        .itemId(String.valueOf(i)).description("offer" + i)
                        .priceReduction(new BigDecimal(i + 1.7))
                        .quantityThreshold(i).id(String.valueOf(i)))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
