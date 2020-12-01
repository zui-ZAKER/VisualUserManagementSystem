package com.entor.bus.service.impl;

import com.entor.bus.entity.BusLocation;
import com.entor.bus.mapper.BusLocationMapper;
import com.entor.bus.service.IBusLocationService;
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
public class BusLocationServiceImpl extends ServiceImpl<BusLocationMapper, BusLocation> implements IBusLocationService {

    private final BusLocationMapper busLocationMapper;

    @Override
    public IPage<BusLocation> findBusLocations(QueryRequest request, BusLocation busLocation) {
        LambdaQueryWrapper<BusLocation> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<BusLocation> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<BusLocation> findBusLocations(BusLocation busLocation) {
	    LambdaQueryWrapper<BusLocation> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBusLocation(BusLocation busLocation) {
        this.save(busLocation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBusLocation(BusLocation busLocation) {
        this.saveOrUpdate(busLocation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBusLocation(BusLocation busLocation) {
        LambdaQueryWrapper<BusLocation> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
