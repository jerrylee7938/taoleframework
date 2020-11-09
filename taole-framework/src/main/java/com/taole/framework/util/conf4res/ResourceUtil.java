package com.taole.framework.util.conf4res;

import com.taole.framework.cache.RedisCache;
import com.taole.framework.util.BooleanUtils;
import com.taole.framework.util.FileUtils;
import com.taole.framework.util.conf4res.conf.CachedConfig;
import com.taole.framework.util.conf4res.conf.CachedConfigBase;
import com.taole.framework.util.conf4res.conf.MemCachedConfig;
import com.taole.framework.util.conf4res.conf.RedisCachedConfig;
import com.taole.framework.web.Log4jWebConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by ChengJ on 2017/5/5.
 */
public class ResourceUtil<T extends CachedConfigBase> {
    private Log logger = LogFactory.getLog(ResourceUtil.class);
    private ClassLoader classLoader;
    private String PR = "WEB-";

    public HashMap<String, T> getMemCachedConfig() {
        return todoMemCachedConfigEntity(getResourcePath("MemCachedConfig.xml").getWarOrJar());
    }

    public HashMap<String, T> getRedisCachedConfig() {
        return todoRedisCachedConfigEntity(getResourcePath("RedisConfig.xml").getWarOrJar());
    }
    public HashMap<String, T> getCachedConfig() {
        return todoCachedConfigEntity(getResourcePath("CachedConfig.xml").getWarOrJar());
    }

    public static String getEhcachePath() {
        return new ResourceUtil<>().getResourcePath("Ehcache.xml").getWarOrJar();
    }

    public ResourceUtil() {
        super();
    }

    public ResourceUtil(ClassLoader classLoader) {
        this();
        this.classLoader = classLoader;
    }

    public T getT4True(HashMap<String, T> map) {
        Set<String> main = map.keySet();
        T config = null;
        for (String s : main) {
            if (BooleanUtils.isTrue(s)) {
                config = map.get(s);
                break;
            } else return null;
        }
        return config;
    }

    public T getT4nothing(HashMap<String, T> map) {
        Set<String> main = map.keySet();
        T config = null;
        for (String s : main) {
            config = map.get(s);
            break;
        }
        return config;
    }


    private HashMap<String, T> todoMemCachedConfigEntity(String conf) {
        File file = new File(conf);
        if (file.exists()) {
            SAXReader sr = new SAXReader();
            Document doc = null;
            try {
                HashMap<String, T> res = new HashMap<>();
                doc = sr.read(file);
                Element Root = doc.getRootElement();
                Element Enabled = (Element) Root.selectSingleNode("Enabled");
                Element Servers = (Element) Root.selectSingleNode("Servers");
                Element Config = (Element) Root.selectSingleNode("Config");
                Boolean enable = Boolean.parseBoolean(Enabled.getText());
                List<Element> serverDoms = Servers.elements();
                MemCachedConfig memCachedConfig = new MemCachedConfig();
                MemCachedConfig.Server server = null;
                memCachedConfig.setEnabled(enable);
                res.put(enable.toString(), (T) memCachedConfig);
                LinkedList servers = new LinkedList();
                memCachedConfig.setServers(servers);
                for (Element serverTmp : serverDoms) {
                    server = new MemCachedConfig.Server().setHost(serverTmp.attributeValue("host")).setPort(serverTmp.attributeValue("port")).setWeight(serverTmp.attributeValue("weight"));
                    servers.add(server);
                }
                MemCachedConfig.Config config = new MemCachedConfig.Config();
                memCachedConfig.setConfig(config);
                Field[] fields = config.getClass().getDeclaredFields();
                giveConf4Me(config, Config);
                return res;
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private HashMap<String, T> todoRedisCachedConfigEntity(String conf) {
        File file = new File(conf);
        if (file.exists()) {
            SAXReader sr = new SAXReader();
            Document doc = null;
            try {
                HashMap<String, T> res = new HashMap<>();
                doc = sr.read(file);
                Element Root = doc.getRootElement();
                Element Enabled = (Element) Root.selectSingleNode("Enabled");
                Element Config = (Element) Root.selectSingleNode("Config");
                Boolean enable = Boolean.parseBoolean(Enabled.getText());
                RedisCachedConfig redisCachedConfig = new RedisCachedConfig();
                redisCachedConfig.setEnabled(enable);
                res.put(enable.toString(), (T) redisCachedConfig);
                RedisCachedConfig.Config config = new RedisCachedConfig.Config();
                redisCachedConfig.setConfig(config);
                Field[] fields = config.getClass().getDeclaredFields();
                giveConf4Me(config, Config);
                return res;
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected Object giveConf4Me(Object t, Element Config) {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field cfgItem : fields) {
            try {
                cfgItem.setAccessible(true);
                Node node = Config.selectSingleNode("//property[@name='" + cfgItem.getName() + "']");
                if (node == null) continue;
                Element configNode = (Element) node;
                cfgItem.set(t, configNode.getTextTrim());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    private HashMap<String, T> todoCachedConfigEntity(String conf) {
        File file = new File(conf);
        if (file.exists()) {
            SAXReader sr = new SAXReader();
            Document doc = null;
            try {
                HashMap<String, T> res = new HashMap<>();
                doc = sr.read(file);
                Element Root = doc.getRootElement();
                Element Enabled = (Element) Root.selectSingleNode("Enabled");
                Element Domains = (Element) Root.selectSingleNode("Domain");
                Element Bizs = (Element) Root.selectSingleNode("Biz");
                Boolean enable = Boolean.parseBoolean(Enabled.getText());
                List<Element> domcaches = Domains.elements();
                List<Element> bizcaches = Bizs.elements();
                CachedConfig cachedConfig = new CachedConfig();
                CachedConfig.Cache cache = null;
                cachedConfig.setEnabled(enable);
                res.put(enable.toString(), (T) cachedConfig);
                LinkedList doms = new LinkedList();
                LinkedList bizs = new LinkedList();
                cachedConfig.setCache4domains(doms);
                cachedConfig.setCache4Bizs(bizs);
                for (Element c : domcaches) {
                    cache = new CachedConfig.Cache().setName(c.attributeValue("name")).setEnabled(BooleanUtils.isTrue(c.attributeValue("enabled"))).setWeight(c.attributeValue("weight"));
                    doms.add(cache);
                }
                for (Element c : bizcaches) {
                    cache = new CachedConfig.Cache().setName(c.attributeValue("name")).setEnabled(BooleanUtils.isTrue(c.attributeValue("enabled"))).setWeight(c.attributeValue("weight"));
                    bizs.add(cache);
                }
                return res;
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public ResourceConfPath getResourcePath(String conf) {
        String root = System.getProperty(Log4jWebConfigurer.WEB_APP_ROOT_KEY_PARAM);
        Thread.currentThread().getContextClassLoader();
        String u = Thread.currentThread().getContextClassLoader().getResource(conf).getPath().substring(1);
        if (StringUtils.isNotBlank(root))
            u = root + "WEB-INF" + File.separator + "classes" + File.separator + "cache" + File.separator;
        File folder = new File(u);
        if (!folder.exists()) folder.mkdirs();
        File file = new File(u + PR + conf);
        if (!file.exists()) {
            InputStream is = this.getClass().getResourceAsStream("/" + conf);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = "";
            StringBuffer content = new StringBuffer();
            try {
                while ((s = br.readLine()) != null)
                    content.append(s);
                FileUtils.createNewFileFlush(file, content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResourceConfPath().setJar(file.getAbsolutePath()).setWar(file.getAbsolutePath()).setWarOrJar(file.getAbsolutePath());
    }


    public final static class ResourceConfPath {
        private String jar;
        private String war;
        private String warOrJar;

        public String getWarOrJar() {
            return StringUtils.isEmpty(warOrJar) ? "" : warOrJar;
        }

        public ResourceConfPath setWarOrJar(String warOrJar) {
            this.warOrJar = warOrJar;
            return this;
        }

        public String getJar() {
            return StringUtils.isEmpty(jar) ? "" : jar;
        }

        public ResourceConfPath setJar(String jar) {
            this.jar = jar;
            return this;
        }

        public String getWar() {
            return StringUtils.isEmpty(war) ? "" : war;
        }

        public ResourceConfPath setWar(String war) {
            this.war = war;
            return this;
        }
    }
}
