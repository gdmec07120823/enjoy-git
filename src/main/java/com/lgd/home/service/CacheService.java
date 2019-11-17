package com.lgd.home.service;

import java.util.Map;

/**
 * Created by LGD on 2019/08/22.
 */
public interface CacheService {
    public void updateToken(String token,String userId,String itemId);

    public boolean removeOldTokens(long limit);

    public  String checkToken(String token);
    public  long hlen(String key);

    public long addToCart(String token,String itemId,long count);

    public Map<Object,Object> hgetall(String key);
}
