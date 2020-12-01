package com.entor.others.mapper;

import com.entor.common.annotation.DataPermission;
import com.entor.others.entity.DataPermissionTest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


@DataPermission(methods = {"selectPage"})
public interface DataPermissionTestMapper extends BaseMapper<DataPermissionTest> {

}
