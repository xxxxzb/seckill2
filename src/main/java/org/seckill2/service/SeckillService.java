package org.seckill2.service;

import org.seckill2.dto.Exposer;
import org.seckill2.dto.SeckillExecution;
import org.seckill2.entity.Seckill;
import org.seckill2.exception.RepeatException;
import org.seckill2.exception.SeckillCloseException;
import org.seckill2.exception.SeckillException;

import java.util.List;

public interface SeckillService {
    //秒杀商品列表
    List<Seckill> seckillList();

    //根据id查一个秒杀商品
    Seckill getById(long id);

    //根据秒杀商品时间，暴露秒杀接口/暴露秒杀即将开始时间
    Exposer exposeUrlOrTime(long id);

    //执行秒杀操作，捕捉可能会发生的运行期异常
    SeckillExecution executeSeckill(long id,long phone,String md5)
            throws SeckillException,SeckillCloseException,RepeatException;

}
