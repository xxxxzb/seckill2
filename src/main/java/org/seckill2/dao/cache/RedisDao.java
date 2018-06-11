package org.seckill2.dao.cache;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.seckill2.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //创建jedisPool连接池
    private final JedisPool jedisPool;
    //
    public RedisDao(String ip ,int port) {
        jedisPool = new JedisPool(ip,port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    //将seckill放入缓存中
    public String  setSeckill(Seckill seckill){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                //将seckill序列化，返回字节数组
                byte[] value = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                String key = "seckill:" + seckill.getSeckillId;
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, value);//返回 yes/错误信息
                return result;

            }finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    //根据id从缓存中拿seckill
    public Seckill getSeckill(long id){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + id;
                byte[] value = jedis.get(key.getBytes());
                if (value!=null){
                    //通过schema创建空对象
                    Seckill seckill = schema.newMessage();
                    //反序列化
                    ProtostuffIOUtil.mergeFrom(value,seckill,schema);
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
