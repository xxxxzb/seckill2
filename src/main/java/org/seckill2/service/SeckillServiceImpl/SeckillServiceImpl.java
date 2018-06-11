package org.seckill2.service.SeckillServiceImpl;

import org.seckill2.dao.SeckillDao;
import org.seckill2.dao.SuccessKilledDao;
import org.seckill2.dao.cache.RedisDao;
import org.seckill2.dto.Exposer;
import org.seckill2.dto.SeckillExecution;
import org.seckill2.entity.Seckill;
import org.seckill2.entity.SuccessKilled;
import org.seckill2.enums.SeckillStateEnum;
import org.seckill2.exception.RepeatException;
import org.seckill2.exception.SeckillCloseException;
import org.seckill2.exception.SeckillException;
import org.seckill2.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    //md5盐
    private static final String salt = "asl09@$%^%*1245*&%!#owqei236";

    //md5处理
    private String getMd5(long id){
        String s = id + salt;
        DigestUtils.md5DigestAsHex(s.getBytes());
        return s;
    }

    //秒杀商品列表
    @Override
    public List<Seckill> seckillList() {
        return seckillDao.selectAll();
    }
    //根据id查一个秒杀商品
    @Override
    public Seckill getById(long id) {
        return seckillDao.selectById(id);
    }

    //根据秒杀商品时间，暴露秒杀接口/暴露秒杀即将开始时间
    @Override
    public Exposer exposeUrlOrTime(long id) {
        //redis优化
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null){
            seckill = seckillDao.selectById(id);

            //如果数据库中也没有，则说明没有该商品信息，返回false
            if (seckill == null){
                return new Exposer(false,id);
            }else {
                String s = redisDao.setSeckill(seckill);
            }

        }else {

            // 当前时间
            Date nowTime = new Date();

            // 商品属性里设定的秒杀开启时间
            Date startTime = seckill.getStartTime();

            // 商品属性里设定的秒杀关闭时间
            Date endTime = seckill.getEndTime();

            if (nowTime.getTime() >= startTime.getTime()
                    && nowTime.getTime() <= endTime.getTime()){
                //秒杀开启，暴露秒杀接口
                String md5 = this.getMd5(id);
                return new Exposer(true,md5,id);
            }else {
                //秒杀未开启，暴露秒杀即将开始时间
                return new Exposer(false,id,nowTime.getTime()
                        ,startTime.getTime(),endTime.getTime());
            }
        }
        return null;
    }

    //执行秒杀操作，捕捉可能会发生的运行期异常
    @Override
    @Transactional // spring boot默认开启了事务
    public SeckillExecution executeSeckill(long id, long phone, String md5)
            throws SeckillException, SeckillCloseException, RepeatException {
        try {
            if (md5 == null || !md5.equals(this.getMd5(id))){
                throw new SeckillException("秒杀数据被篡改了");
            }

            int insertCount = successKilledDao.insertOne(id, phone);
            if (insertCount == 0){
                throw new RepeatException("重复秒杀");

            }else {

                Date nowTime = new Date();
                int updateCount = seckillDao.reduceNumber(id, nowTime);
                if (updateCount == 0){
                    throw new SeckillCloseException("秒杀已经结束");

                }else {
                    SuccessKilled successKilled = successKilledDao.selectByIdWithSeckill(id, phone);
                    return new SeckillExecution(id, SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        } catch (RepeatException e2) {
            throw e2;
        }catch (SeckillCloseException e3) {
            throw e3;
        } catch (SeckillException e1) {
            throw e1;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常，转化为运行期异常
            throw new SeckillException("运行期内部错误:"+e.getMessage());
        }
    }
}
