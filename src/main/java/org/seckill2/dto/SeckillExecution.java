package org.seckill2.dto;

import lombok.Data;
import org.seckill2.entity.SuccessKilled;
import org.seckill2.enums.SeckillStateEnum;

@Data
public class SeckillExecution {

    private long seckillId;
    //    秒杀结果状态
    private int state;
    //    状态结果解释
    private String stateInfo;
    //    当秒杀成功时，需要传递秒杀成功的对象回去
    private SuccessKilled successKilled;

    //构造方法：秒杀失败返回的信息
    public SeckillExecution(long seckillId, SeckillStateEnum state) {
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }
    //构造方法：秒杀成功返回的信息
    public SeckillExecution(long seckillId, SeckillStateEnum state, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.successKilled = successKilled;
    }
}
