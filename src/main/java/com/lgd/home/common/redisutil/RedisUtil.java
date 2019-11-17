package com.lgd.home.common.redisutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by LGD on 2019/08/22.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置缓存失效时间
     * @param key 键
     * @param time 时间（秒）
     * @return
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回缓存过期时间
     * @param key 键 不能为空
     * @return 时间(秒) 返回0代表永远有效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    public  boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存，可以传一个或多个值
     * @param key
     */
    public void del(String...key){
        if (key!=null&&key.length>0){
            if (key.length==1){
                redisTemplate.delete(key[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取普通缓存
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 存入普通缓存
     * @param key 键
     * @param value 值
     * @return 处理结果
     */
    public boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 存入普通缓存并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) 值为>0 否则设置无限期
     * @return 处理结果
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param delta 递增数量(>0)
     * @return
     */
    public long incr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 递增数量(>0)
     * @return
     */
    public long decr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * 获取hashKey对应所有值
     * @param key 键
     * @return 对应多个键值
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 多个项值
     * @return
     */
    public boolean hmset(String key,Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 多个项值
     * @param time 时间(秒)
     * @return
     */
    public boolean hmset(String key,Map<String,Object> map,long time){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中存入数据，不存在则创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return
     */
    public boolean hset(String key,String item,Object value){
        try {
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中存入数据，不存在则创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在hash，则会替换原hash的时间
     * @return 成功true 失败false
     */
    public boolean hset(String key,String item,Object value,long time){
        try {
            redisTemplate.opsForHash().put(key,item,value);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash中的项
     * @param key 键 不能为null
     * @param item 项 可以多个 不能为null
     */
    public void hdel(String key,Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表是否存在该项值
     * @param key 键
     * @param item 项
     * @return 存在返回true不存在返回false
     */
    public boolean hHasKey(String key,String item){
        return redisTemplate.opsForHash().hasKey(key,item);
    }

    /**
     * hash 递增
     * @param key 键
     * @param item 项
     * @param by 递增值(大于0)
     * @return
     */
    public double hincr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key,item,by);
    }

    /**
     * hash 递减
     * @param key 键
     * @param item 项
     * @param by 减少值(大于0)
     * @return
     */
    public double hdecr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key,item,-by);
    }

    public long hlen(String key){
        try {
           return redisTemplate.opsForHash().size(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
//=============================================set===============================
    /**
     * 根据key获取set所有制
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key){
        try {
          return   redisTemplate.opsForSet().members(key);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 根据value查询是否存在set
     * @param key 键
     * @param value 值
     * @return true存在，false不存在
     */
    public boolean sHasKey(String key,Object value){
        try {
            return redisTemplate.opsForSet().isMember(key,value);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以多个
     * @return
     */
    public long sSet(String key,Object... values){
        try {
            return redisTemplate.opsForSet().add(key,values);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存并设置时间
     * @param key 键
     * @param time 时间(秒>0)
     * @param values 值 可以多个
     * @return 成功个数
     */
    public long sSetAndTime(String key,long time,Object... values){
        try {
            long count= redisTemplate.opsForSet().add(key,values);
            if(time>0){
                expire(key,time);
            }
            return count;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存长度
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以多个
     * @return 移除数量
     */
    public long setRemove(String key,Object... values){
        try {
            return redisTemplate.opsForSet().remove(key,values);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }


//===============================================list==================================
    /**
     * 获取list缓存内容
     * @param key 键
     * @param start 开始
     * @param end 0到-1 代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key,start,end);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存长度
     * @param key 键
     * @return
     */
    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引获取list的值
     * @param key 键
     * @param index 索引 index>0时，0为表头，0.1.2..以此类推为第1个第2个第3个;index<0时，-1为表头,-1.-2.-3..以此类推为倒数第1个第2个第3个
     * @return
     */
    public Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key,index);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key,Object value){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒>0)
     * @return
     */
    public boolean lSet(String key,Object value,long time){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            if(time>0) expire(key,time);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key,List<Object> value){
        try {
            redisTemplate.opsForList().rightPushAll(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒>0)
     * @return
     */
    public boolean lSet(String key,List<Object> value,long time){
        try {
            redisTemplate.opsForList().rightPushAll(key,value);
            if(time>0) expire(key,time);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public  boolean lUpdateIndex(String key,long index, Object value){
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value的数据
     * @param key 键
     * @param count 移除个数
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key,long count,Object value){
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    //===============================================zset==================================

    /**
     * zset插入
     * @param key 键
     * @param value 值
     * @param score 序列
     */
    public void zadd(String key,Object value,double score){
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 移除对应下标zset数据
     * @param key 键
     * @param strat 起始
     * @param end 结束
     * @return 移除的个数
     */
    public long zremrangByRank(String key,long strat,long end){
        try {
            return redisTemplate.opsForZSet().removeRange(key, strat, end);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询对应key存在个数
     * @param key
     * @return
     */
    public long zCard(String key){
        try {
            return redisTemplate.opsForZSet().zCard(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public Set<Object> zrang(String key, long start, long end){
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public long zrem(String key,Object... value){
        try {
            return redisTemplate.opsForZSet().remove(key,value);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
