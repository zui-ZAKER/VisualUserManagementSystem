package com.entor.bus.service.impl;

import com.entor.bus.entity.BusLine;
import com.entor.bus.mapper.BusLineMapper;
import com.entor.bus.service.IBusLineService;
import com.entor.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BusLineServiceImpl extends ServiceImpl<BusLineMapper, BusLine> implements IBusLineService {

    private final BusLineMapper busLineMapper;

    @Override
    public IPage<BusLine> findBusLines(QueryRequest request, BusLine busLine) {
        LambdaQueryWrapper<BusLine> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<BusLine> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<BusLine> findBusLines(BusLine busLine) {
	    LambdaQueryWrapper<BusLine> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBusLine(BusLine busLine) {
        this.save(busLine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBusLine(BusLine busLine) {
        this.saveOrUpdate(busLine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBusLine(BusLine busLine) {
        LambdaQueryWrapper<BusLine> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}

    @Override
    public BusLine findByName(String name) {
        return busLineMapper.findByName(name);
    }

    @Override
    public List<BusLine> findBusLineByName(String name) {
        LambdaQueryWrapper<BusLine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),BusLine::getName,name);
        return this.baseMapper.selectList(queryWrapper);
    }

}
