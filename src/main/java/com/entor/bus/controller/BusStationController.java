package com.entor.bus.controller;

import com.entor.bus.entity.BusStation;
import com.entor.bus.service.IBusStationService;
import com.entor.common.annotation.ControllerEndpoint;
import com.entor.common.controller.BaseController;
import com.entor.common.entity.FebsConstant;
import com.entor.common.entity.FebsResponse;
import com.entor.common.entity.QueryRequest;
import com.entor.common.utils.FebsUtil;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class BusStationController extends BaseController {

    private final IBusStationService busStationService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "busStation")
    public String busStationIndex(){
        return FebsUtil.view("busStation/busStation");
    }

    @GetMapping("busStation")
    @ResponseBody
    @RequiresPermissions("busStation:view")
    public FebsResponse getAllBusStations(BusStation busStation) {
        return new FebsResponse().success().data(busStationService.findBusStations(busStation));
    }

    @GetMapping("busStation/list")
    @ResponseBody
    @RequiresPermissions("busStation:view")
    public FebsResponse busStationList(QueryRequest request, BusStation busStation) {
        Map<String, Object> dataTable = getDataTable(this.busStationService.findBusStations(request, busStation));
        return new FebsResponse().success().data(dataTable);
    }

    @ResponseBody
    @PostMapping("busStation/add")
    @ControllerEndpoint(operation = "新增BusStation", exceptionMessage = "新增BusStation失败")
    @RequiresPermissions("busStation:add")
    public FebsResponse addBusStation(@Valid BusStation busStation) {
        this.busStationService.createBusStation(busStation);
        return new FebsResponse().success();
    }

    @ResponseBody
    @ControllerEndpoint(operation = "删除BusStation", exceptionMessage = "删除BusStation失败")
    @GetMapping("busStation/delete/{ids}")
    @RequiresPermissions("busStation:delete")
    public FebsResponse deleteBusStation(@NotBlank(message = "{required}") @PathVariable String ids) {
        this.busStationService.deleteBusStation(ids);
        return new FebsResponse().success();
    }
    @PostMapping("busStation/update")
    @ResponseBody
    @ControllerEndpoint(operation = "修改BusStation", exceptionMessage = "修改BusStation失败")
    @RequiresPermissions("busStation:update")
    public FebsResponse updateBusStation(@Valid BusStation busStation) {
        this.busStationService.updateBusStation(busStation);
        return new FebsResponse().success();
    }
    @ControllerEndpoint(operation = "修改BusStation", exceptionMessage = "导出Excel失败")
    @PostMapping("busStation/excel")
    @ResponseBody
    @RequiresPermissions("busStation:export")
    public void export(QueryRequest queryRequest, BusStation busStation, HttpServletResponse response) {
        List<BusStation> busStations = this.busStationService.findBusStations(queryRequest, busStation).getRecords();
        ExcelKit.$Export(BusStation.class, response).downXlsx(busStations, false);
    }

    @PostMapping("busStation/getLocationByName/{name}")
    @ResponseBody
    public Map<String, BigDecimal> getLocationByName(@PathVariable String name){
        System.out.println("name:"+name);
        BusStation busStation = busStationService.findByName(name);
        Map<String,BigDecimal> map = new HashMap<>();
        map.put("longitude",busStation.getLongitude());
        map.put("latitude",busStation.getLatitude());
        return map;
    }

}
