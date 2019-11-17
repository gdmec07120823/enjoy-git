package com.lgd.home;

import com.lgd.home.common.redisutil.RedisUtil;
import com.lgd.home.service.CacheService;
import com.lgd.home.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomewebApplicationTests {

	@Autowired
	UserService userService;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	CacheService cacheService;

	@Test
	public void contextLoads() {
		System.out.println(userService.selectUsers());
	}

	@Test
	public void loginTest() throws InterruptedException{
		String username="lgd";
		String itemId=null;
		System.out.println("用户-----"+username+"------登录成功-------");
		String token= UUID.randomUUID().toString();

		cacheService.updateToken(token,username,itemId);
		System.out.println("刚刚将令牌更新为："+token+"当前操作用户是："+username);

		String user=cacheService.checkToken(token);
		System.out.println("获取对应token用户为:"+user);

		cacheService.removeOldTokens(3);
		long s=cacheService.hlen("login:info");
		System.out.println("当前系统登录cokkie剩余个数："+s);
	}
	@Test
	public void putToCartTest() throws InterruptedException{
		String username="lgd";
		String itemId="xiaomi";
		System.out.println("用户-----"+username+"------登录成功-------");
		String token= UUID.randomUUID().toString();

		System.out.println("开始刷新session");

		cacheService.updateToken(token,username,itemId);

		long i=cacheService.addToCart(token,itemId,3);
		if (i>0){
			System.out.println("加入购物车成功");
		}else {
			System.out.println("加入购物车失败");
		}

		Map<Object, Object> r=cacheService.hgetall("cart:"+token);
		System.out.println("用户 "+username+" 的购物车里有如下商品:");
		for (Map.Entry<Object, Object> entry:r.entrySet()){
			System.out.println(" "+entry.getKey()+": "+entry.getValue()+"/");
		}

	}

	@Test
	public void updateToken(){
//		redisUtil.zadd("test","cctv",121);
//		redisUtil.zadd("test","cctv1",12);
//		redisUtil.zadd("test","cctv2",13);
//		redisUtil.zadd("test","cctv5",16);
//		redisUtil.zadd("test","cctv6",17);
//		redisUtil.zadd("test","cctv7",18);
//		redisUtil.zadd("test","cctv3",14);
//		redisUtil.zadd("test","cctv4",15);
		redisUtil.zremrangByRank("test",0,-3);
		if (redisUtil.hasKey("james")){
			System.out.println("exists james");
		}else {
			redisUtil.set("james",1);
		}
		System.out.println(
				redisUtil.incr("james",1));
	}

}
