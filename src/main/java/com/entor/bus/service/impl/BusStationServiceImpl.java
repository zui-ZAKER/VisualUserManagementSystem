package com.entor.bus.service.impl;

import com.entor.bus.entity.BusStation;
import com.entor.bus.mapper.BusStationMapper;
import com.entor.bus.service.IBusStationService;
import com.entor.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BusStationServiceImpl extends ServiceImpl<BusStationMapper, BusStation> implements IBusStationService {

    private final BusStationMapper busStationMapper;

    @Override
    public IPage<BusStation> findBusStations(QueryRequest request, BusStation busStation) {
        LambdaQueryWrapper<BusStation> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<BusStation> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<BusStation> findBusStations(BusStation busStation) {
	    LambdaQueryWrapper<BusStation> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBusStation(BusStation busStation) {
        this.save(busStation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBusStation(BusStation busStation) {
        this.saveOrUpdate(busStation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBusStation(String ids) {
//        LambdaQueryWrapper<BusStation> wrapper = new LambdaQueryWrapper<>();
//	    // TODO 设置删除条件
//	    this.remove(wrapper);
        this.removeById(ids);
	}

    @Override
    public BusStation findByName(String name) {
        return busStationMapper.findByName(name);
    }


}
