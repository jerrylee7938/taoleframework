package com.taole.cache.memcached;

import java.io.Serializable;

/**
 * Created by ChengJ on 2017/5/2.
 */
public interface MemWrapper extends Serializable{
    void initConfig();
    void initConfig4Govern();
}
