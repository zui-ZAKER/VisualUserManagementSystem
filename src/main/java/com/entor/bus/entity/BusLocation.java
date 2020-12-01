package com.entor.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_bus_location")
public class BusLocation {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车牌号
     */
    @TableField("num")
    private Integer num;

    /**
     * 车牌号
     */
    @TableField("line")
    private String line;

    /**
     * 
     */
    @TableField("last_longitude")
    private BigDecimal lastLongitude;
    /**
     * 
     */
    @TableField("last_latitude")
    private BigDecimal lastLatitude;
}
