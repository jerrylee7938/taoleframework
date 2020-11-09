import org.springframework.stereotype.Component;

@Component
public class TaoleCacheBoot {
    private ClassLoader classLoader;
    private String[] confgs;
    private String mapcache;
    private String ehcache;
    private String memCachedConfig;
    private String redisConfig;
    private String bizCacheNodeConfig;
    private String storageProductConfig;
    private String instanceTrusteeshipProductConfig;

    public static void init(String... config) {

    }

    //需要限定好的名字
    public TaoleCacheBoot(String... config) {
        this.confgs = config;
    }

    public TaoleCacheBoot(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public TaoleCacheBoot() {

    }


    public TaoleCacheBoot setMapcache(String mapcache) {
        this.mapcache = mapcache;
        return this;
    }

    public TaoleCacheBoot setEhcache(String ehcache) {
        this.ehcache = ehcache;
        return this;
    }

    public TaoleCacheBoot setMemCachedConfig(String memCachedConfig) {
        this.memCachedConfig = memCachedConfig;
        return this;
    }

    public TaoleCacheBoot setRedisConfig(String redisConfig) {
        this.redisConfig = redisConfig;
        return this;
    }

    public TaoleCacheBoot setBizCacheNodeConfig(String bizCacheNodeConfig) {
        this.bizCacheNodeConfig = bizCacheNodeConfig;
        return this;
    }

    public TaoleCacheBoot setStorageProductConfig(String storageProductConfig) {
        this.storageProductConfig = storageProductConfig;
        return this;
    }

    public TaoleCacheBoot setInstanceTrusteeshipProductConfig(String instanceTrusteeshipProductConfig) {
        this.instanceTrusteeshipProductConfig = instanceTrusteeshipProductConfig;
        return this;
    }

    public void boot(){}

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
