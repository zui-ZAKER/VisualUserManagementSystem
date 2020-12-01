package com.entor.bus.controller;

import com.entor.bus.entity.BusLine;
import com.entor.bus.entity.BusStation;
import com.entor.bus.service.IBusLineService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class BusLineController extends BaseController {

    private final IBusLineService busLineService;

    private final IBusStationService busStationService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "busLine")
    public String busLineIndex(){
        return FebsUtil.view("busLine/busLine");
    }

    @GetMapping("busLine")
    @ResponseBody
    @RequiresPermissions("busLine:view")
    public FebsResponse getAllBusLines(BusLine busLine) {
        return new FebsResponse().success().data(busLineService.findBusLines(busLine));
    }

    @GetMapping("busLine/list")
    @ResponseBody
    @RequiresPermissions("busLine:view")
    public FebsResponse busLineList(QueryRequest request, BusLine busLine) {
        Map<String, Object> dataTable = getDataTable(this.busLineService.findBusLines(request, busLine));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增BusLine", exceptionMessage = "新增BusLine失败")
    @PostMapping("busLine/add")
    @ResponseBody
    @RequiresPermissions("busLine:add")
    public FebsResponse addBusLine(@Valid BusLine busLine, BindingResult bindingResult) {
        this.busLineService.createBusLine(busLine);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除BusLine", exceptionMessage = "删除BusLine失败")
    @GetMapping("busLine/delete/{ids}")
    @ResponseBody
    @RequiresPermissions("busLine:delete")
    public FebsResponse deleteBusLine(@NotBlank(message = "{required}") @PathVariable String ids) {
        this.busLineService.removeById(ids);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改BusLine", exceptionMessage = "修改BusLine失败")
    @PostMapping("busLine/update")
    @ResponseBody
    @RequiresPermissions("busLine:update")
    public FebsResponse updateBusLine(BusLine busLine) {
        this.busLineService.updateBusLine(busLine);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改BusLine", exceptionMessage = "导出Excel失败")
    @PostMapping("busLine/excel")
    @ResponseBody
    @RequiresPermissions("busLine:export")
    public void export(QueryRequest queryRequest, BusLine busLine, HttpServletResponse response) {
        List<BusLine> busLines = this.busLineService.findBusLines(queryRequest, busLine).getRecords();
        ExcelKit.$Export(BusLine.class, response).downXlsx(busLines, false);
    }

    @GetMapping("busLine/search/{name}")
    @ResponseBody
    public FebsResponse busLineSearch(@PathVariable String name) {
        List<BusLine> busLine = this.busLineService.findBusLineByName(name);
        return new FebsResponse().success().data(busLine);
    }

    @PostMapping("busLine/map/{name}")
    @ResponseBody
    public List<Map<String,Object>> busLineStation(@PathVariable String name){
        List<Map<String,Object>> list = new ArrayList<>();
        BusLine busLine = busLineService.findByName(name);
        for(String s:busLine.getRoute().split(",")){
            Map<String,Object> map = new LinkedHashMap<>();
            BusStation busStation = busStationService.getById(Integer.parseInt(s));
            map.put("name",busStation.getName());
            map.put("longitude",busStation.getLongitude());
            map.put("latitude",busStation.getLatitude());
            list.add(map);
        }
        return list;
    }

}
