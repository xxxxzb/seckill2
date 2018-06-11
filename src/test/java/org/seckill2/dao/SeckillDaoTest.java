package org.seckill2.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill2.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void test() {
//        System.out.println(seckillDao.selectAll());
//        System.out.println(seckillDao.selectById(1001));
//        System.out.println(seckillDao.reduceNumber(1003,new  Date()));
        System.out.println(successKilledDao.selectByIdWithSeckill(1001,11111111111L));
    }


}