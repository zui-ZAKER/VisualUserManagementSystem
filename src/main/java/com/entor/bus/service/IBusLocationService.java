package com.entor.bus.service;

import com.entor.bus.entity.BusLocation;
import com.entor.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBusLocationService extends IService<BusLocation> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param busLocation busLocation
     * @return IPage<BusLocation>
     */
    IPage<BusLocation> findBusLocations(QueryRequest request, BusLocation busLocation);

    /**
     * 查询（所有）
     *
     * @param busLocation busLocation
     * @return List<BusLocation>
     */
    List<BusLocation> findBusLocations(BusLocation busLocation);

    /**
     * 新增
     *
     * @param busLocation busLocation
     */
    void createBusLocation(BusLocation busLocation);

    /**
     * 修改
     *
     * @param busLocation busLocation
     */
    void updateBusLocation(BusLocation busLocation);

    /**
     * 删除
     *
     * @param busLocation busLocation
     */
    void deleteBusLocation(BusLocation busLocation);
}
