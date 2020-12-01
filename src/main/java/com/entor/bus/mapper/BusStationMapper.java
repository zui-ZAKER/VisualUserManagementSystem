package com.entor.bus.mapper;

import com.entor.bus.entity.BusStation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface BusStationMapper extends BaseMapper<BusStation> {

    @Select("select * from t_bus_station where name like '%${name}%'")
    public BusStation findByName(String name);

}
