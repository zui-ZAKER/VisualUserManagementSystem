package com.entor.bus.mapper;

import com.entor.bus.entity.BusLine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface BusLineMapper extends BaseMapper<BusLine> {

    @Select("select * from t_bus_line where name like '%${name}%'")
    public BusLine findByName(String name);

}
