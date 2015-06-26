package com.dabast.common.cache.decorators;


import com.dabast.common.cache.Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 注意：在一同一个事务的上下文中，如果对一个对象先删除又查询，应该返回一个空的对象，防止脏数据发生
 */
public class TransactionalCache implements Cache {

    private Cache delegate;
    private boolean clearOnCommit;
    private Map<Object, AddEntry> entriesToAddOnCommit;
    private Map<Object, RemoveEntry> entriesToRemoveOnCommit;

    public TransactionalCache(Cache delegate) {
        this.delegate = delegate;
        this.clearOnCommit = false;
        this.entriesToAddOnCommit = new HashMap<Object, AddEntry>();
        this.entriesToRemoveOnCommit = new HashMap<Object, RemoveEntry>();
    }

    public Cache getDelegate() {
        return delegate;
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
    }

    public Object getObject(Object key) {
        //如果此对象在删除的事务中，将不返回对象的值
        if(entriesToRemoveOnCommit.containsKey(key)){
            return null;
        }
        //如果此对象在增加的事务中，可以返回对象
        if(entriesToAddOnCommit.containsKey(key)){
            return entriesToAddOnCommit.get(key).value;
        }
        return delegate.getObject(key);
    }

    @Override
    public List<Object> getObjects(List<Object> keys) {
        List<Object> realKeys = new ArrayList<Object>(keys);
        List<Object> result = new ArrayList<Object>();
        for (Object key:keys){
            if(entriesToRemoveOnCommit.containsKey(key) && entriesToAddOnCommit.containsKey(key)){//先去掉本地事务中的对象
                realKeys.remove(key);
            }

            //如果此对象在增加的事务中，可以返回对象
            if(entriesToAddOnCommit.containsKey(key)){
                result.add(entriesToAddOnCommit.get(key).value);
            }
        }

        result.addAll(delegate.getObjects(realKeys));
        return result;
    }

    public ReadWriteLock getReadWriteLock() {
        return delegate.getReadWriteLock();
    }

    public void putObject(Object key, Object object) {
        //如果此对象在删除的事务中，就不允许再增加了
        if(!entriesToRemoveOnCommit.containsKey(key)){
            entriesToAddOnCommit.put(key, new AddEntry(delegate, key, object));
        }
//        entriesToRemoveOnCommit.remove(key);

    }

    public Object removeObject(Object key) {
        Object value = delegate.getObject(key);
        entriesToAddOnCommit.remove(key);
        entriesToRemoveOnCommit.put(key, new RemoveEntry(delegate, key));
        return value;
    }

    public void clear() {
        reset();
        clearOnCommit = true;
    }

    public void commit() {
        delegate.getReadWriteLock().writeLock().lock();
        try {
            if (clearOnCommit) {
                delegate.clear();
            } else {
                for (RemoveEntry entry : entriesToRemoveOnCommit.values()) {
                    entry.commit();
                }
            }
            for (AddEntry entry : entriesToAddOnCommit.values()) {
                entry.commit();
            }
            reset();
        } finally {
            delegate.getReadWriteLock().writeLock().unlock();
        }
    }

    public void rollback() {
        reset();
    }

    private void reset() {
        clearOnCommit = false;
        entriesToRemoveOnCommit.clear();
        entriesToAddOnCommit.clear();
    }

    private static class AddEntry {
        private Cache cache;
        private Object key;
        private Object value;

        public AddEntry(Cache cache, Object key, Object value) {
            this.cache = cache;
            this.key = key;
            this.value = value;
        }

        public void commit() {
            cache.putObject(key, value);
        }
    }

    private static class RemoveEntry {
        private Cache cache;
        private Object key;

        public RemoveEntry(Cache cache, Object key) {
            this.cache = cache;
            this.key = key;
        }

        public void commit() {
            cache.removeObject(key);
        }
    }


}
