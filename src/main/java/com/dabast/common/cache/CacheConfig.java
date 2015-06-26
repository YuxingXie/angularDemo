package com.dabast.common.cache;

/**
 * User: lxd
 * Date: 11-7-28
 * Time: 下午6:03
 */
public class CacheConfig {
    private String id;
    private Integer size = 1024;
    private boolean readWrite = true;
    private boolean isLogging;

    public boolean isLogging() {
        return isLogging;
    }

    public void setLogging(boolean logging) {
        isLogging = logging;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isReadWrite() {
        return readWrite;
    }

    public void setReadWrite(boolean readWrite) {
        this.readWrite = readWrite;
    }
}
