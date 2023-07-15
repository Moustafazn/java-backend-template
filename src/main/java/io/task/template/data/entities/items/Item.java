package io.task.template.data.entities.items;

import io.task.template.data.constants.Constants;
import io.task.template.data.entities.AuditedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item extends AuditedEntity {

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false, columnDefinition = Constants.MONEY_SQL_DEFINITION)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false, columnDefinition = Constants.MONEY_SQL_DEFINITION)
    private BigDecimal cost;

}
