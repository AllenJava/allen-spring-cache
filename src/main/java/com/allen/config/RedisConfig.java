package com.allen.config;

import java.nio.charset.Charset;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Configuration
public class RedisConfig {
    
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer=new FastJsonRedisSerializer<>(Object.class);
        
        /**
         * 安全升级包禁用了部分autotype的功能，也就是"@type"这种指定类型的功能会被限制在一定范围内使用。如果你使用场景中包括了这个功能
         * 在1.2.28/1.2.29以及所有的.sec01版本中，有多重保护，但打开autotype之后仍会存在风险，不建议打开，而是使用一个较小范围的白名单。打开autoType建议升级到最新版本1.2.37以上.
         * 详情地址：https://github.com/alibaba/fastjson/wiki/enable_autotype
         */
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        ParserConfig.getGlobalInstance().addAccept("com.allen.config."); 
        
        // 设置值（value）的序列化采用FastJsonRedisSerializer
        stringRedisTemplate.setValueSerializer(fastJsonRedisSerializer);
        stringRedisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        
        //设置键（key）的序列化采用重写后的StringRedisSerializer
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }
    
    /**
     * spring cache使用RedisCacheManager对象
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheManager redisCacheManager=new RedisCacheManager(stringRedisTemplate(redisConnectionFactory));
        //默认过期时间
        //redisCacheManager.setDefaultExpiration(300);
        // 启动时加载远程缓存
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        //是否使用前缀生成器
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
    
    
    /**
     * 
    * @ClassName: FastJsonRedisSerializer
    * @Description: 重写FastJsonRedisSerializer
    * @author chenliqiao
    * @date 2018年7月23日 下午4:55:41
    * 
    * @param <T>
     */
    public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
        
        public final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
     
        private Class<T> clazz;
     
        public FastJsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }
     
        @Override
        public byte[] serialize(T t){
            if (t == null) {
                return new byte[0];
            }
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }
     
        @Override
        public T deserialize(byte[] bytes){
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            String str = new String(bytes, DEFAULT_CHARSET);
            return (T) JSON.parseObject(str, clazz);
        }
     
    }

    
    
    /**
     * 
    * @ClassName: StringRedisSerializer
    * @Description: 重写StringRedisSerializer
    *               注：使用StringRedisSerializer做key的序列化时，StringRedisSerializer的泛型指定的是String，
    *                 传其他对象就会报类型转换错误，在使用@Cacheable注解是key属性就只能传String进来。把这个序列化方式重写了，将泛型改成Object。
    * @author chenliqiao
    * @date 2018年7月23日 下午3:08:58
    *
     */
    public class StringRedisSerializer implements RedisSerializer<Object>{
        
        private final Charset charset;
        
        private final String target = "\"";
        
        private final String replacement = "";

        public StringRedisSerializer() {
            this(Charset.forName("UTF8"));
        }

        public StringRedisSerializer(Charset charset) {
            Assert.notNull(charset, "Charset must not be null!");
            this.charset = charset;
        }

        @Override
        public byte[] serialize(Object t){
            String string=JSON.toJSONString(t);
            if(string==null)
                return null; 
            string=string.replace(target, replacement);
            return (string == null ? null : string.getBytes(charset));
        }

        @Override
        public String deserialize(byte[] bytes) {
            return (bytes == null ? null : new String(bytes, charset));
        }
        
    }

}
