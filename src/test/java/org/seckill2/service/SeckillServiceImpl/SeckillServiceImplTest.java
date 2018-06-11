package org.seckill2.service.SeckillServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill2.dto.Exposer;
import org.seckill2.exception.RepeatException;
import org.seckill2.exception.SeckillCloseException;
import org.seckill2.exception.SeckillException;
import org.seckill2.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillService seckillService;


    @Test
    public void testSeckillLogic() {
        long id = 1003;
        Exposer exposer = seckillService.exposeUrlOrTime(id);
        if (exposer.isExposer) {
            long phone = 18909099999L;
            String md5 = exposer.getMd5();
            try {
                seckillService.executeSeckill(id, phone, md5);
            } catch (RepeatException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        }else {
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }

    }
}