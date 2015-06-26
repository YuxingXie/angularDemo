package com.dabast.common.cache;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: lxd
 * Date: 11-8-1
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
public interface ICacheFactory {
    void addHistory(String id, String querykey);
    Set<String> getHistory(String id);
    Cache getCache(String id);
    Set getAllCacheId();
    void rollback();
    void begin();
    void commit();
}
