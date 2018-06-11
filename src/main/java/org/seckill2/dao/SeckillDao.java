package org.seckill2.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.seckill2.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    //查所有
    @Select("select * from seckill")
    List<Seckill> selectAll();

    //根据id查一个
    @Select("select * from seckill where seckill_id = #{id}")
    Seckill selectById(long id);

    //减库存
    @Update("update seckill set number=number-1 " +
            "where seckill_id = #{id} " +
            "and start_time <= #{killTime} " +
            "and end_time >= #{killTime}")
    int reduceNumber(@Param("id")long id,@Param("killTime") Date killTime);
}
