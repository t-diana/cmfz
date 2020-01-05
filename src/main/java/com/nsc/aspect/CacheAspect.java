package com.nsc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


@Component   //将这个组件交给spring工厂进行管理
@Aspect     //声明这是一个切面
public class CacheAspect{
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 因为将redis实现了与stringredis相应的序列化后 redis与StringRedis 的大key
     * 不一致
     * 清除时采用stringredis
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //添加和获取缓存采用环绕通知的原因：

    /**
     * 前置通知只可以查询或者添加 不能进行后续操作
     * 操作不完整
     */
    //声明相应的通知
    @Around("@annotation(com.nsc.annotation.AddCache)")
    public Object aroundAspect(ProceedingJoinPoint proceedingJoinPoint) {
        //将redis进行与stringredis 进行相应的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //获取数据结构模型
        HashOperations hashOperations = redisTemplate.opsForHash();
        //创建相应的数据结构
        //最外层的大key 也就是mapper中Dao
        String firstKey = proceedingJoinPoint.getTarget().getClass().getName();
        //获取小key   就是方法名加参数
        //获取相应的方法名
        String secondKey = proceedingJoinPoint.getSignature().getName();
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        //进行方法与参数拼接
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(secondKey);
        for (Object arg : args) {
            //查看arg的实际值
            System.out.println("参数是"+arg.toString());
            stringBuilder.append(arg.toString());
        }
        //先判断缓存存不存在 不存在 则添加缓存   否则将缓存中的数据进行返回
        if (hashOperations.hasKey(firstKey, stringBuilder)) {
            System.out.println("获取缓存");
            Object o = hashOperations.get(firstKey, stringBuilder);
            return o;
        }
        //查询数据
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("添加缓存");
        //添加缓存
        hashOperations.put(firstKey, stringBuilder, proceed);
        return proceed;
    }

    /**
     * 清除缓存
     */
    @After("@annotation(com.nsc.annotation.ClearCache)")
    public void afterWrite(JoinPoint joinPoint) {
        //清除大key
        System.out.println("清除缓存");
        String firstKey = joinPoint.getTarget().getClass().getName();
        stringRedisTemplate.delete(firstKey);
    }
}
