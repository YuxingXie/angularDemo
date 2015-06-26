package com.dabast.common.cache.decorators;


import com.dabast.common.cache.Cache;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class LoggingCache implements Cache {
    private Cache delegate;
    private int requests = 0;
    private int hits = 0;
    private int writes = 0;
    private int removes = 0;
    private int maxSize = 0;

    public LoggingCache(Cache delegate) {
        this.delegate = delegate;
    }

    public String getId() {
        return delegate.getId();
    }

    public int getSize() {
        return delegate.getSize();
    }

    @Override
    public void setSize(int size) {
        delegate.setSize(size);
        maxSize = size;
    }

    public void putObject(Object key, Object object) {
        delegate.putObject(key, object);
        writes++;
    }

    public Object getObject(Object key) {
        requests++;
        final Object value = delegate.getObject(key);
        if (value != null) {
            hits++;
        }
        return value;
    }

    @Override
    public List<Object> getObjects(List<Object> keys) {
        requests++;
        final List<Object> values = delegate.getObjects(keys);
        if (values != null) {
            hits++;
        }
        return values;
    }

    public Object removeObject(Object key) {
        Object obj = delegate.removeObject(key);
        removes++;
        return obj;
    }

    public void clear() {
        delegate.clear();
    }

    public ReadWriteLock getReadWriteLock() {
        return delegate.getReadWriteLock();
    }

    public int hashCode() {
        return delegate.hashCode();
    }

    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

//    private double getHitRatio() {
//        return (double) hits / (double) requests;
//    }


    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getWrites() {
        return writes;
    }

    public void setWrites(int writes) {
        this.writes = writes;
    }

    public int getRemoves() {
        return removes;
    }

    public void setRemoves(int removes) {
        this.removes = removes;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
