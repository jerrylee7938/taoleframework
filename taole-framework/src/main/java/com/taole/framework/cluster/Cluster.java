package com.taole.framework.cluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.taole.cache.memcached.MemApi;

public class Cluster extends Global {
    private MemApi getMemcachedClient() {
        return MemApi.getInstance();
    }

    String nodesKey = "__global_cluster_nodes__";

    @SuppressWarnings("unchecked")
    public void register(Node node) {
        super.register(node);
        List<Node> nodes = (List<Node>) getMemcachedClient().get(nodesKey);
        if (nodes == null) {
            nodes = new ArrayList<Node>();
        }
        // 删除之前缓存里的节点
        for (Iterator<Node> ite = nodes.iterator(); ite.hasNext(); ) {
            Node exist = ite.next();
            if (exist.equals(node)) {
                ite.remove();
                // nodes.remove(exist);
            }
        }
        nodes.add(node);
        getMemcachedClient().set(nodesKey, nodes);

    }

    /**
     * {@inheritDoc}
     */
    public Object get(String key) {
        return getMemcachedClient().get(toMemcachedKey(key));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Node> getNodeList() {
        List<Node> nodes = (List<Node>) getMemcachedClient().get(nodesKey);
        if (nodes == null) {
            nodes = new ArrayList<Node>();
        }
        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    public void put(String key, Object value) {
        getMemcachedClient().set(toMemcachedKey(key), value);
    }

    public String toMemcachedKey(String key) {
        return "__global_cluster_node_" + key + "__";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String key) {
        getMemcachedClient().delete(toMemcachedKey(key));
    }

    public String toLockKey(String name) {
        return "__global_lock_" + name + "__";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean lock(String name) {
        if (islock(name)) {
            return false;
        } else {
            getMemcachedClient().set(toLockKey(name), Boolean.TRUE);
            return true;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unlock(String name) {
        try {
            getMemcachedClient().set(toLockKey(name), Boolean.FALSE);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean islock(String name) {
        return getMemcachedClient().get(toLockKey(name)) == Boolean.TRUE;
    }

}
