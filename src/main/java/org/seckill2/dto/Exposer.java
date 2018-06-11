package org.seckill2.dto;

import lombok.Data;

@Data
public class Exposer {
    //是否开启秒杀
    private boolean exposed;

    //加密措施
    private String md5;

    //秒杀商品ID
    private long seckillId;

    //系统当前时间(毫秒)
    private long now;

    //秒杀的开启时间
    private long start;

    //秒杀的结束时间
    private long end;

    //构造方法：秒杀商品不存在，不暴露接口
    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    //构造方法：秒杀开启，暴露接口，MD5是商品ID经过加密后的值
    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    //构造方法：秒杀未开启，不暴露接口
    public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }
}
