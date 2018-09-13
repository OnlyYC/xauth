package com.liaoyb.auth.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liaoyb.auth.modules.sys.domain.SysRolePermission;
import com.liaoyb.auth.modules.sys.repository.SysRolePermissionMapper;
import com.liaoyb.auth.modules.sys.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * 角色权限关系
 *
 * @author liaoyanbo
 */
@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

}
