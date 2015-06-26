package com.dabast.common.cache.decorators;


import com.dabast.common.cache.Cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Lru (first in, first out) cache decorator
 */
public class LruCache implements Cache {

    private final Cache delegate;
    private Map<Object, Object> keyMap;
    private Object eldestKey;

    public LruCache(Cache delegate) {
        this.delegate = delegate;
        setSize(1024);
    }

    public String getId() {
        return delegate.getId();
    }

    public int getSize() {
        return delegate.getSize();
    }

    public void setSize(final int size) {
        keyMap = new LinkedHashMap<Object, Object>(size, .75F, true) {
            private static final long serialVersionUID = 4267176411845948333L;

            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                boolean tooBig = size() > size;
                if (tooBig) {
                    eldestKey = eldest.getKey();
                }
                return tooBig;
            }
        };
        delegate.setSize(size);
    }

    public void putObject(Object key, Object value) {
        delegate.putObject(key, value);
        cycleKeyList(key);
    }

    public Object getObject(Object key) {
        keyMap.get(key); //touch
        return delegate.getObject(key);

    }

    @Override
    public List<Object> getObjects(List<Object> keys) {
        for (Object key:keys){
            keyMap.get(key); //touch
        }
        return delegate.getObjects(keys);
    }

    public Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    public void clear() {
        delegate.clear();
        keyMap.clear();
    }

    public ReadWriteLock getReadWriteLock() {
        return delegate.getReadWriteLock();
    }

    private void cycleKeyList(Object key) {
        keyMap.put(key, key);
        if (eldestKey != null) {
            delegate.removeObject(eldestKey);
            eldestKey = null;
        }
    }

}

