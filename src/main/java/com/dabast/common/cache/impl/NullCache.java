package com.dabast.common.cache.impl;

import com.dabast.common.cache.Cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 关闭缓存时，使用了一个空的实现类
 * User: lxd
 * Date: 11-7-29
 * Time: 下午4:31
 */
public class NullCache implements Cache {
    private String id;


    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public NullCache(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return 0;
    }

    @Override
    public void setSize(int size) {

    }

    public void putObject(Object key, Object value) {

    }

    public Object getObject(Object key) {
        return null;
    }

    @Override
    public List<Object> getObjects(List<Object> keys) {
        return new ArrayList<Object>();
    }

    public Object removeObject(Object key) {
        return null;
    }

    public void clear() {
    }

    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public boolean equals(Object o) {
        if (this == o){ return true; }
        if (!(o instanceof Cache)) {return false;}

        Cache otherCache = (Cache) o;
        return getId().equals(otherCache.getId());
    }

    public int hashCode() {
        return getId().hashCode();
    }
}
