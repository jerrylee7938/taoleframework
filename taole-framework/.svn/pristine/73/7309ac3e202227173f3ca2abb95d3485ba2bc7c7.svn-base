package com.taole.framework.cache;

import com.taole.framework.bean.DynamicBeanFactory;
import com.taole.framework.manager.DomainEngineFactory;
import com.taole.framework.module.ModuleRegistry;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * Created by ChengJ on 2017/5/9.
 */
@Configuration("fw.cacheFactoryConfig")
public class CachRegistryConfig {

    @Bean(name = "map.cacheFactory")
    CacheFactory globalCacheFactory() {
        return new MapCacheFactoryImpl();
    }

    @Bean(name = "e.cacheFactory")
    CacheFactory eCacheFactory() {
        return new EhCacheFactoryImpl();
    }

    @Bean(name = "mem.cacheFactory")
    CacheFactory memCacheFactory() {
        return new DangaMemcachedImpl();
    }

    @Bean(name = "red.CacheFactory")
    CacheFactory redCacheFactory() {
        return new RedisCachedImpl();
    }

    @Bean
    CacheFactoryFactory cacheFactoryFactory() {
        return new CacheFactoryFactory();
    }

}
