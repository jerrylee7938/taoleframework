package com.taole.cache.redis;

public interface TaoleCacheApi<T> {
    Object get(T value);

    boolean set(T value);

    boolean del(T value);

    boolean lock(T value, int millisecond);
}
