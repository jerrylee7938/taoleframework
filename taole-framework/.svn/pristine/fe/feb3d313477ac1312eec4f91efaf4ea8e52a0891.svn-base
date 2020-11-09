package com.taole.cache.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;

public class SpyMemcachedClientSource {

    private String servers = "127.0.0.1:11210";

    /**
     * @return the servers
     */
    public String getServers() {
        return servers;
    }

    /**
     * @param servers the servers to set
     */
    public void setServers(String servers) {
        this.servers = servers;
    }

    private MemcachedClient client = null;

    public synchronized MemcachedClient getMemcachedClient() throws IOException {
        if (client == null) {
            String[] addresses = servers.split(",");
            List<InetSocketAddress> isadds = new ArrayList<InetSocketAddress>();
            for (String address : addresses) {
                String[] ipAndPort = address.split(":");
                String ip = ipAndPort[0];
                int port = Integer.parseInt(ipAndPort[1], 10);
                isadds.add(new InetSocketAddress(ip, port));
            }
            if (isadds.size() == 1) {
                client = new MemcachedClient(isadds.get(0));
            } else {
                client = new MemcachedClient(isadds);
            }

        }
        return client;
    }

    public static void main(String[] args) {
        try {
            MemcachedClient memcachedClient = new SpyMemcachedClientSource().getMemcachedClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
