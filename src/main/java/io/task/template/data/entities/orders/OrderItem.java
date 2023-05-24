package io.task.template.data.entities.orders;

import io.task.template.data.constants.Constants;
import io.task.template.data.entities.AuditedEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "order")
@EqualsAndHashCode(exclude = {"order"})
public class OrderItem extends AuditedEntity {

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, columnDefinition = Constants.MONEY_SQL_DEFINITION)
    private BigDecimal originalPrice;

    @NotNull
    @Column(nullable = false, columnDefinition = Constants.MONEY_SQL_DEFINITION)
    private BigDecimal discountedPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
