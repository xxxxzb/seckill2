package org.seckill2.entity;

import lombok.Data;

import java.util.Date;
@Data
public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private short state;
    private Date createTime;
    //多对一，
    private Seckill seckill;
}
