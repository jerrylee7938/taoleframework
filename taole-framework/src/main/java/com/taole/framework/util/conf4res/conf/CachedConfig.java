package com.taole.framework.util.conf4res.conf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CachedConfig extends CachedConfigBase {

    private LinkedList<Cache> cache4domains;
    private LinkedList<Cache> cache4Bizs;

    public LinkedList<Cache> getCache4domains() {
        return cache4domains;
    }

    public void setCache4domains(LinkedList<Cache> cache4domains) {
        this.cache4domains = cache4domains;
    }

    public LinkedList<Cache> getCache4Bizs() {
        return cache4Bizs;
    }

    public void setCache4Bizs(LinkedList<Cache> cache4Bizs) {
        this.cache4Bizs = cache4Bizs;
    }

    public static class Cache {
        private String name;
        private boolean enabled;
        private String weight;

        public String getName() {
            return name;
        }

        public Cache setName(String name) {
            this.name = name;
            return this;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public Cache setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public String getWeight() {
            return weight;
        }

        public Cache setWeight(String weight) {
            this.weight = weight;
            return this;
        }
    }


    public Cache getDomainConfigCache(LinkedList<Cache> cache4) {
        if (null == cache4) return null;
        Cache top = null;
        for (Cache cache : cache4) {
            if (cache.isEnabled()) {
                if (null == top) top = cache;
                if (null != top)
                    if (Integer.parseInt(cache.getWeight()) > Integer.parseInt(top.getWeight())) top = cache;
            }
        }
        return top;
    }


    public Cache getDomainConfigCache() {
        return getDomainConfigCache(cache4domains);
    }

    public Cache getBizConfigCache() {
        return getDomainConfigCache(cache4Bizs);
    }
}



