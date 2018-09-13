package com.liaoyb.auth.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.liaoyb.auth.modules.sys.domain.SysPermission;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {
    /**
     * 获取户权限
     *
     * @return
     */
    List<SysPermission> getUserPermission(String userId);
}
