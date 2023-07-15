package io.task.template.data.entities.orders;

import io.task.template.data.entities.AuditedEntity;
import io.task.template.data.entities.items.Item;
import io.task.template.data.entities.offers.Offer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "order_offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"order", "offer"})
@EqualsAndHashCode(exclude = {"order", "offer"})
public class OrderOffer extends AuditedEntity {

    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
