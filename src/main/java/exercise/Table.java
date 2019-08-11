package exercise;


import exercise.enums.EntityType;
import exercise.model.EntityBase;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Table {

    private List<EntityBase> items;

    private EntityType entityType;

    public Table(List<EntityBase> items, EntityType entityType) {
        this.items = items;
        this.entityType = entityType;
    }

    public List<EntityBase> getItems() {
        return items;
    }

    public void setItems(List<EntityBase> items) {
        this.items = items;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

}
