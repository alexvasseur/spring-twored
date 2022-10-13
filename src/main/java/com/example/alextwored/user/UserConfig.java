package com.example.alextwored.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSocketConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(
        redisTemplateRef = "tworedisTPL",//bean get registered first and overrides default
        basePackages = "com.example.alextwored.user",//this DOES then remove PersonRepository from autowired
        keyValueTemplateRef = "tworedisKV"
)
public class UserConfig {

    @Bean
    RedisConnectionFactory twoRedisCNF() {
        RedisConfiguration rc = new RedisStandaloneConfiguration("localhost", 6380);
        LettuceConnectionFactory lcf = new LettuceConnectionFactory(rc);
        return lcf;
    }

    @Bean(name = "tworedisTPL")
    public RedisTemplate<String, String> twoRedisTPL(RedisConnectionFactory rcf) {
        RedisTemplate<String, String> rt = new RedisTemplate<>();
        rt.setConnectionFactory(twoRedisCNF());
        return rt;
    }

    @Bean(name = "tworedisKV")
    public KeyValueTemplate twoRedisKV() {
        RedisKeyValueTemplate rc = new RedisKeyValueTemplate(
                new RedisKeyValueAdapter(twoRedisTPL(twoRedisCNF())),
                new RedisMappingContext()
        );
        return rc;
    }


}
