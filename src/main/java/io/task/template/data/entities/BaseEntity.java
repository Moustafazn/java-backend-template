package io.task.template.data.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}

