package com.dabast.common.cache;

import com.dabast.common.cache.decorators.TransactionalCache;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * User: lxd
 * Date: 11-7-29
 * Time: 上午11:42
 */
public class TransactionAwareCacheFactory implements ICacheFactory {
    private ICacheFactory proxy;
    private static ThreadLocal<CacheTransactionDefinition> transactionalCaches = new ThreadLocal<CacheTransactionDefinition>();

    public void commit() {
        for (TransactionalCache txCache : getDefinition().getTransactionStatus().values()) {
            txCache.commit();
        }
        getDefinition().getTransactionStatus().clear();
    }

    public void rollback() {
        for (TransactionalCache txCache : getDefinition().getTransactionStatus().values()) {
            txCache.rollback();
        }
        getDefinition().getTransactionStatus().clear();
    }

    public void begin(){
        getDefinition();
    }

    private CacheTransactionDefinition getDefinition(){
        CacheTransactionDefinition ctDefinition = transactionalCaches.get();
        if (ctDefinition == null) {
            ctDefinition = new CacheTransactionDefinition();
            transactionalCaches.set(ctDefinition);
        }
        return ctDefinition;
    }

    public void setProxy(ICacheFactory proxy) {
        this.proxy = proxy;
    }

    @Override
    public void addHistory(String id, String querykey) {
        proxy.addHistory(id,querykey);
    }

    @Override
    public Set<String> getHistory(String id) {
        return proxy.getHistory(id);
    }

    public Cache getCache(String id) {
        return getTransactionalCache(id);
    }

    @Override
    public Set getAllCacheId() {
        return proxy.getAllCacheId();
    }

    private TransactionalCache getTransactionalCache(String id) {
        CacheTransactionDefinition ctDefinition = getDefinition();
        TransactionalCache txCache = ctDefinition.transactionStatus.get(id);
        if(txCache==null){
            Cache cache = proxy.getCache(id);
            txCache = new TransactionalCache(cache);
            ctDefinition.transactionStatus.put(id, txCache);
        }
        return txCache;
    }

    public static class CacheTransactionDefinition {
        private Map<String, TransactionalCache> transactionStatus;

        public CacheTransactionDefinition() {
            transactionStatus = new Hashtable<String, TransactionalCache>();
        }

        public Map<String, TransactionalCache> getTransactionStatus() {
            return transactionStatus;
        }
    }
}
