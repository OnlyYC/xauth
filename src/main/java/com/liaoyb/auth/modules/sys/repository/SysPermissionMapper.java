package com.liaoyb.auth.modules.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liaoyb.auth.modules.sys.domain.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 获取用户权限
     *
     * @param userId
     * @return
     */
    List<SysPermission> getUserPermission(String userId);
}
