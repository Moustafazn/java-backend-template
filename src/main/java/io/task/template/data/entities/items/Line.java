package io.task.template.data.entities.items;

import io.task.template.data.entities.AuditedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line extends AuditedEntity {

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id")
    private Item item;
}
