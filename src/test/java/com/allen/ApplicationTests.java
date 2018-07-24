package com.allen;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.allen.dao.UserRepository;
import com.allen.dao.entity.UserInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    
    @Resource
    private UserRepository userRepository;
    
    @Resource
    private CacheManager cacheManager;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testUser(){
	    UserInfo user1=userRepository.findById(13);
	    System.out.println("第一次查询："+user1);
	    
	    UserInfo user2=userRepository.findById(13);
        System.out.println("第二次查询："+user2); 
        
        System.out.println("cacheManager："+cacheManager.getCache("users"));
        
//        //delete
//        //userRepository.delete(11);
//        System.out.println("第三次查询："+userRepository.findById(12));
        
        //update or add
        UserInfo updateEntity=new UserInfo();
        updateEntity.setId(13);
        updateEntity.setName("spring-cache-update");
        UserInfo entity=userRepository.save(updateEntity);
        System.out.println("第四次查询："+userRepository.findById(entity.getId()));
        
//        //add
//        UserInfo addEntity=new UserInfo();
//        addEntity.setName("spring-cache-add");
//        UserInfo addResult=userRepository.save(addEntity);
//        System.out.println("第五次查询："+userRepository.findById(addResult.getId()));
//        System.out.println("第六次查询："+userRepository.findById(addResult.getId()));
	    
	}
	

}
