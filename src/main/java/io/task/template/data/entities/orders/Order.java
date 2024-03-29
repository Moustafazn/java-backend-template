package io.task.template.data.entities.orders;

import io.task.template.data.entities.AuditedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AuditedEntity {

    @NotNull
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OrderItem> items;

    @NotNull
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OrderOffer> offers;
}
