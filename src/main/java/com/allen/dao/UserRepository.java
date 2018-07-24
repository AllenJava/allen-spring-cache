package com.allen.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.allen.dao.entity.UserInfo;

@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<UserInfo,Integer>{
    
    @Cacheable(key="#p0",condition="#p0>12")//#p0表示方法的第一个参数
    UserInfo findById(Integer id);
    
    @CacheEvict
    void delete(Integer id);
    
    @SuppressWarnings("unchecked")
    @CachePut(key="#p0.getId()") 
    UserInfo save(UserInfo userInfo);
    
}
