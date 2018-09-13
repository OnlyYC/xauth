package com.liaoyb.auth.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.liaoyb.auth.modules.sys.domain.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    List<SysRole> getUserRole(String userId);

    /**
     * 获取所有角色
     *
     * @return
     */
    List<SysRole> getRoles();
}
