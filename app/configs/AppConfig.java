package configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
@EnableCaching
@ComponentScan({"controllers", "services"})
public class AppConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehcache());
    }

    @Bean
    public net.sf.ehcache.CacheManager ehcache() {
        try {
            Resource config = new ClassPathResource("ehcache.xml");
            net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.create(config.getInputStream());
            for (String s : cacheManager.getCacheNames()) {
                System.out.println("Cache => " + s);
            }
            return cacheManager;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}