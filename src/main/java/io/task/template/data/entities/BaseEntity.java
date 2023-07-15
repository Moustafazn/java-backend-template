package io.task.template.data.entities;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

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

