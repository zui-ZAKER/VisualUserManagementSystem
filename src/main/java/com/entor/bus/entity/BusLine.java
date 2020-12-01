package com.entor.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("t_bus_line")
public class BusLine {

    /**
     * 公交线路
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公交线路名称
     */
    @TableField("name")
    private String name;

    /**
     * 首班车时间
     */
    @DateTimeFormat(pattern="HH:mm:ss")
    @JsonFormat(pattern="HH:mm:ss",timezone = "GMT+8")
    @TableField("begin_time")
    private Date beginTime;

    /**
     * 末班车时间
     */
    @DateTimeFormat(pattern="HH:mm:ss")
    @JsonFormat(pattern="HH:mm:ss",timezone = "GMT+8")
    @TableField("end_time")
    private Date endTime;

    /**
     * 票价
     */
    @TableField("price")
    private Integer price;

    /**
     * 途径的车站编号，1,2,3,4,5,6
     */
    @TableField("route")
    private String route;

}
