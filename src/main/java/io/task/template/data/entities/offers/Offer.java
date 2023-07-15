package io.task.template.data.entities.offers;

import io.task.template.data.constants.Constants;
import io.task.template.data.entities.AuditedEntity;
import io.task.template.data.entities.items.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer extends AuditedEntity {
    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    @Column(nullable = false, columnDefinition = Constants.MONEY_SQL_DEFINITION)
    private BigDecimal priceReduction;

    @NotNull
    @Column(nullable = false)
    private Integer quantityThreshold;
}
