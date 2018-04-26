package com.comba.server.dao.common.hibernate;

public class EntityUpdatedEvent extends EntityEvent {
    private static final long serialVersionUID = 0L;

    public EntityUpdatedEvent(Object entity) {
        super(entity);
    }

    public boolean isUpdated() {
        return true;
    }
}
