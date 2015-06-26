package com.dabast.common.cache.impl;


import com.dabast.common.cache.Cache;
import com.dabast.common.cache.CacheException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PerpetualCache implements Cache {

  private String id;

  private Map<Object, Object> cache = new HashMap<Object, Object>();

  private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  public PerpetualCache(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public int getSize() {
    return cache.size();
  }

    @Override
    public void setSize(int size) {

    }

    public void putObject(Object key, Object value) {
    cache.put(key, value);
  }

  public Object getObject(Object key) {
    return cache.get(key);
  }

    @Override
    public List<Object> getObjects(List<Object> keys) {
        List<Object> result = new ArrayList<Object>();
        for (Object key:keys){
            Object val = getObject(key);
            if(val!=null){
                result.add(val);
            }
        }
        return result;
    }

    public Object removeObject(Object key) {
    return cache.remove(key);
  }

  public void clear() {
    cache.clear();
  }

  public ReadWriteLock getReadWriteLock() {
    return readWriteLock;
  }


}
