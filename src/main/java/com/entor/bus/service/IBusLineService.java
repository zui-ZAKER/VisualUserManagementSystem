package com.entor.bus.service;

import com.entor.bus.entity.BusLine;
import com.entor.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBusLineService extends IService<BusLine> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param busLine busLine
     * @return IPage<BusLine>
     */
    IPage<BusLine> findBusLines(QueryRequest request, BusLine busLine);

    /**
     * 查询（所有）
     *
     * @param busLine busLine
     * @return List<BusLine>
     */
    List<BusLine> findBusLines(BusLine busLine);

    /**
     * 新增
     *
     * @param busLine busLine
     */
    void createBusLine(BusLine busLine);

    /**
     * 修改
     *
     * @param busLine busLine
     */
    void updateBusLine(BusLine busLine);

    /**
     * 删除
     *
     * @param busLine busLine
     */
    void deleteBusLine(BusLine busLine);

    BusLine findByName(String name);

    List<BusLine> findBusLineByName(String name);
}
