package io.github.saber.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.github.saber.modules.sys.domain.SysPermission;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {
    /**
     * 获取户权限
     *
     * @return
     */
    List<SysPermission> getUserPermission(String userId);
}
