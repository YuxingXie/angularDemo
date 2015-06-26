package com.dabast.common.cache;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public interface Cache {

    String getId();

    int getSize();

    void setSize(int size);

    void putObject(Object key, Object value);

    Object getObject(Object key);

    List<Object> getObjects(List<Object> keys);

    Object removeObject(Object key);

    void clear();

    ReadWriteLock getReadWriteLock();

}