package com.taole.framework.util.conf4res.conf;

import java.util.LinkedList;

public class MemCachedConfig extends CachedConfigBase {
    private LinkedList<Server> Servers;
    private Config config;

    public LinkedList<Server> getServers() {
        return Servers;
    }

    public void setServers(LinkedList<Server> servers) {
        Servers = servers;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static class Config {
        private String initConn;
        private String minConn;
        private String maxConn;
        private String maxIdle;
        private String maintSleep;
        private String socketTO;
        private String socketConnTO;

        public String getInitConn() {
            return initConn;
        }

        public void setInitConn(String initConn) {
            this.initConn = initConn;
        }

        public String getMinConn() {
            return minConn;
        }

        public void setMinConn(String minConn) {
            this.minConn = minConn;
        }

        public String getMaxConn() {
            return maxConn;
        }

        public void setMaxConn(String maxConn) {
            this.maxConn = maxConn;
        }

        public String getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(String maxIdle) {
            this.maxIdle = maxIdle;
        }

        public String getMaintSleep() {
            return maintSleep;
        }

        public void setMaintSleep(String maintSleep) {
            this.maintSleep = maintSleep;
        }

        public String getSocketTO() {
            return socketTO;
        }

        public void setSocketTO(String socketTO) {
            this.socketTO = socketTO;
        }

        public String getSocketConnTO() {
            return socketConnTO;
        }

        public void setSocketConnTO(String socketConnTO) {
            this.socketConnTO = socketConnTO;
        }
    }

    public static class Server {
        private String host;
        private String port;
        private String weight;

        public String getHost() {
            return host;
        }

        public Server setHost(String host) {
            this.host = host;
            return this;
        }

        public String getPort() {
            return port;
        }

        public Server setPort(String port) {
            this.port = port;
            return this;
        }

        public String getWeight() {
            return weight;
        }

        public Server setWeight(String weight) {
            this.weight = weight;
            return this;
        }
    }
}