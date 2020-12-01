package com.entor.bus.service;

import com.entor.bus.entity.BusStation;
import com.entor.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBusStationService extends IService<BusStation> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param busStation busStation
     * @return IPage<BusStation>
     */
    IPage<BusStation> findBusStations(QueryRequest request, BusStation busStation);

    /**
     * 查询（所有）
     *
     * @param busStation busStation
     * @return List<BusStation>
     */
    List<BusStation> findBusStations(BusStation busStation);

    /**
     * 新增
     *
     * @param busStation busStation
     */
    void createBusStation(BusStation busStation);

    /**
     * 修改
     *
     * @param busStation busStation
     */
    void updateBusStation(BusStation busStation);

    /**
     * 删除
     *
     */
    void deleteBusStation(String ids);

    BusStation findByName(String name);

}
