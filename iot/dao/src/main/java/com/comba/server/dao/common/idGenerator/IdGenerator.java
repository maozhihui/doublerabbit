package com.comba.server.dao.common.idGenerator;

public interface IdGenerator {
    long generateId();

    long generateId(String name);

    long generateId(Class<?> clz);
}
