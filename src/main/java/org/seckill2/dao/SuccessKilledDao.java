package org.seckill2.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.seckill2.entity.SuccessKilled;

public interface SuccessKilledDao {

    //插入购买明细
    @Insert("insert into success_killed (seckill_id,user_phone,state) " +
            "value(#{id},#{phone},0)")
    int insertOne(@Param("id")long id,@Param("phone")long phone);

    //根据id查一个并携带秒杀产品对象实体
    @Select("select sk.seckill_id,sk.user_phone,sk.state,sk.create_time," +
            "s.seckill_id \"seckill.seckillId\"," +
            "s.name \"seckill.name\"," +
            "s.number \"seckill.number\"," +
            "s.start_time \"seckill.startTime\"," +
            "s.end_time \"seckill.endTime\"," +
            "s.create_time \"seckill.createTime\" " +
            "from success_killed sk , seckill s " +
            "where sk.seckill_id=s.seckill_id " +
            "and sk.seckill_id=#{id} " +
            "and sk.user_phone=#{phone}")
    SuccessKilled selectByIdWithSeckill(@Param("id")long id,@Param("phone")long phone);

}
