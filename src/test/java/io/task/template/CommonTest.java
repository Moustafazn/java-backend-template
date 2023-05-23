package io.task.template;

import io.task.template.api.v1.resources.*;
import io.task.template.data.dtos.LinesDto;
import io.task.template.data.entities.items.Item;
import io.task.template.data.entities.items.Line;
import io.task.template.data.entities.offers.Offer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommonTest {

    protected static final String URL = "/api/v1";


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
        return new Offer("offer" + index, "offer description" + index, this.getTestItem(index), new BigDecimal(index + 1.2), index);
    }

    protected List<Offer> getTestOffers(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(i -> this.getTestOffer(i))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected Item getTestItem(final int index) {
        return new Item("item" + index, "Item description" + index, new BigDecimal(index + 1.2), new BigDecimal(index + 1.7));
    }

    protected LinesResource getTestLinesResource(final int count) {
        return new LinesResource().lines(getTestLinesSource(count));
    }

    protected ItemsManagementLineListResource getTestItemsManagementLineListResource(final int count) {
        return new ItemsManagementLineListResource().lines(getTestItemsManagementLineResource(count));
    }

    protected List<LineResource> getTestLinesSource(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(i -> new LineResource().quantity(i).item(this.getTestItemSource(i)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected List<ItemsManagementLineResource> getTestItemsManagementLineResource(final int count) {
        return IntStream
                .range(0, count)
                .mapToObj(i -> new ItemsManagementLineResource().quantity(i).item(this.getTestItemsManagementItemResource(i)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected ItemResource getTestItemSource(final int index) {
        return new ItemResource().name("item" + index).
                description("Item description" + index).
                price(new BigDecimal(index + 1.2)).id(String.valueOf(index));
    }

    protected ItemsManagementItemResource getTestItemsManagementItemResource(final int index) {
        return new ItemsManagementItemResource().name("item" + index).
                description("Item description" + index).
                price(new BigDecimal(index + 1.2)).id(String.valueOf(index))
                .cost(new BigDecimal(index + 13));
    }


}
