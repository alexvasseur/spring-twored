package com.example.alextwored.person;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(
        //redisTemplateRef = "garbage",//this DOES fail
        redisTemplateRef = "oneredisTPL",
        basePackages = "com.example.alextwored.person",//this DOES then remove PersonRepository from autowired
        keyValueTemplateRef = "oneredisKV"
)
public class PersonConfig {

    @Bean
    RedisConnectionFactory oneRedisCNF() {
        RedisConfiguration rc = new RedisStandaloneConfiguration("localhost", 6379);
        LettuceConnectionFactory lcf = new LettuceConnectionFactory(rc);
        return lcf;
    }

    @Bean(name = "oneredisTPL")
    public RedisTemplate<String, String> oneRedisTPL(RedisConnectionFactory rcf) {
        RedisTemplate<String, String> rt = new RedisTemplate<>();
        rt.setConnectionFactory(oneRedisCNF());
        return rt;
    }

    @Bean(name = "oneredisKV")
    public KeyValueTemplate oneRedisKV() {
        RedisKeyValueTemplate rc = new RedisKeyValueTemplate(
            new RedisKeyValueAdapter(oneRedisTPL(oneRedisCNF())),
            new RedisMappingContext()
        );
        return rc;
    }


    //RedisKeyValueTemplate, RedisKeyValueAdapter, RedisMappingContext

}
