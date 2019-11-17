package com.lgd.home.service.impl;

import com.lgd.home.common.redisutil.RedisUtil;
import com.lgd.home.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by LGD on 2019/08/22.
 */
@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void updateToken(String token, String userId, String itemId) {
        long timestamp=System.currentTimeMillis()/1000;
        redisUtil.hset("login:info",token,userId);//绑定token与用户
        redisUtil.zadd("recent:info",token,timestamp);//记录用户登录时间
        if(itemId!=null){
            redisUtil.zadd("viewed:"+token,itemId,timestamp);//记录用户浏览的商品
            redisUtil.zremrangByRank("viewed:"+token,0,-26);//删除第0到-26条数据
        }
    }

    @Override
    public boolean removeOldTokens(long limit) {
        long size=redisUtil.zCard("recent:info");//查找所有令牌树
        if(size<=limit){//判断是否超了
            return false;
        }
        long endindex=size-limit;//获取需要删除个数
        Set<Object> tokenSet=redisUtil.zrang("recent:info",0,endindex-1);//获取旧登录记录
        String[] tokens=tokenSet.toArray(new String[tokenSet.size()]);//加入到数组中

        ArrayList<String> sessionKeys=new ArrayList<>();
        for (String token:tokens){
            sessionKeys.add("viewed:"+token);//构建需要删除的浏览记录令牌
        }

        redisUtil.del(sessionKeys.toArray(new String[sessionKeys.size()]));//删除浏览记录
        redisUtil.hdel("login:info",tokens);//删除令牌信息
        redisUtil.zrem("recent:info",tokens);//删除最近登录记录


        return true;
    }

    @Override
    public String checkToken(String token) {
        return redisUtil.hget("login:info",token).toString();
    }

    @Override
    public long hlen(String key) {
        return redisUtil.hlen(key);
    }

    @Override
    public long addToCart(String token, String itemId, long count) {
        if(count<=0){
            redisUtil.hdel("cart:"+token,itemId);
            return 0L;
        }else {
            return redisUtil.hset("cart:"+token,itemId,count)?1L:0L;
        }
    }

    @Override
    public Map<Object, Object> hgetall(String key) {
        return redisUtil.hmget(key);
    }
}
