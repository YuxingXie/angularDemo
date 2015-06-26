package com.dabast.common.cache.decorators;



import com.dabast.common.cache.Cache;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * FIFO (first in, first out) cache decorator
 */
public class FifoCache implements Cache {

    private final Cache delegate;
    private final Deque<Object> keyList;
    private int size;

    public FifoCache(Cache delegate) {
        this.delegate = delegate;
        this.keyList = new LinkedList<Object>();
        this.size = 1024;
    }

    public String getId() {
        return delegate.getId();
    }

    public int getSize() {
        return delegate.getSize();
    }

    public void setSize(int size) {
        this.size = size;
        delegate.setSize(size);
    }

    public void putObject(Object key, Object value) {
        cycleKeyList(key);
        delegate.putObject(key, value);
    }

    public Object getObject(Object key) {
        return delegate.getObject(key);
    }

    @Override
    public List<Object> getObjects(List<Object> keys) {
        return delegate.getObjects(keys);
    }

    public Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    public void clear() {
        delegate.clear();
        keyList.clear();
    }

    public ReadWriteLock getReadWriteLock() {
        return delegate.getReadWriteLock();
    }

    private void cycleKeyList(Object key) {
        keyList.addLast(key);
        if (keyList.size() > size) {
            Object oldestKey = keyList.removeFirst();
            delegate.removeObject(oldestKey);
        }
    }

}
