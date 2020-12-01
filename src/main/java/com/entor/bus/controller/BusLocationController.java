package com.entor.bus.controller;

import com.entor.bus.entity.BusLine;
import com.entor.bus.entity.BusLocation;
import com.entor.bus.entity.BusStation;
import com.entor.bus.service.IBusLineService;
import com.entor.bus.service.IBusLocationService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class BusLocationController extends BaseController {

    private final IBusLocationService busLocationService;

    private final IBusLineService busLineService;

    private final IBusStationService busStationService;

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "busLocation")
    public String busLocationIndex(){
        return FebsUtil.view("busLocation/busLocation");
    }

    @GetMapping("busLocation")
    @ResponseBody
    @RequiresPermissions("busLocation:list")
    public FebsResponse getAllBusLocations(BusLocation busLocation) {
        return new FebsResponse().success().data(busLocationService.findBusLocations(busLocation));
    }

    @GetMapping("busLocation/list")
    @ResponseBody
    @RequiresPermissions("busLocation:list")
    public FebsResponse busLocationList(QueryRequest request, BusLocation busLocation) {
        Map<String, Object> dataTable = getDataTable(this.busLocationService.findBusLocations(request, busLocation));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增BusLocation", exceptionMessage = "新增BusLocation失败")
    @PostMapping("busLocation")
    @ResponseBody
    @RequiresPermissions("busLocation:add")
    public FebsResponse addBusLocation(@Valid BusLocation busLocation) {
        this.busLocationService.createBusLocation(busLocation);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除BusLocation", exceptionMessage = "删除BusLocation失败")
    @GetMapping("busLocation/delete")
    @ResponseBody
    @RequiresPermissions("busLocation:delete")
    public FebsResponse deleteBusLocation(BusLocation busLocation) {
        this.busLocationService.deleteBusLocation(busLocation);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改BusLocation", exceptionMessage = "修改BusLocation失败")
    @PostMapping("busLocation/update")
    @ResponseBody
    @RequiresPermissions("busLocation:update")
    public FebsResponse updateBusLocation(BusLocation busLocation) {
        this.busLocationService.updateBusLocation(busLocation);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改BusLocation", exceptionMessage = "导出Excel失败")
    @PostMapping("busLocation/excel")
    @ResponseBody
    @RequiresPermissions("busLocation:export")
    public void export(QueryRequest queryRequest, BusLocation busLocation, HttpServletResponse response) {
        List<BusLocation> busLocations = this.busLocationService.findBusLocations(queryRequest, busLocation).getRecords();
        ExcelKit.$Export(BusLocation.class, response).downXlsx(busLocations, false);
    }

    @PostMapping("busLocation/getBusLocation/{busId}/{longitude}/{latitude}")
    @ResponseBody
    public Map<String,Object> getBusLocation(@PathVariable Integer busId, @PathVariable BigDecimal longitude, @PathVariable BigDecimal latitude){
        BusLocation busLocation = new BusLocation();
        busLocation.setId(busId);
        busLocation.setNum(123);
        busLocation.setLastLongitude(longitude);
        busLocation.setLastLatitude(latitude);
        this.busLocationService.updateBusLocation(busLocation);

        String name = null;
        double a = 10000;
        List<Map<String,BigDecimal>> list = new ArrayList<>();
        BusLine busLine = busLineService.findByName(busLocationService.getById(busLocation.getId()).getLine());
        Map<String,Object> map = new HashMap<>();
        for(String s:busLine.getRoute().split(",")){
            BusStation busStation = busStationService.getById(Integer.parseInt(s));
            double b  = getDistance(busStation.getLatitude(),busStation.getLongitude(),latitude,longitude);
            if(a>b){
                a = b;
                name = busStation.getName();
            }
        }
        map.put("distance",a);
        map.put("name",name);
        return map;
    }


    //通过经纬度计算两点的距离算法
    public static double getDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2,
                                     BigDecimal lng2) {
        double la1 = lat1.doubleValue();
        double la2 = lat2.doubleValue();
        double ln1 = lng1.doubleValue();
        double ln2 = lng2.doubleValue();
        double radLat1 = rad(la1);
        double radLat2 = rad(la2);
        double a = radLat1 - radLat2;
        double b = rad(ln1) - rad(ln2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;
        return s;
    }
}
