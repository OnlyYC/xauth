package io.github.saber.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.github.saber.modules.sys.domain.SysRolePermission;
import io.github.saber.modules.sys.repository.SysRolePermissionMapper;
import io.github.saber.modules.sys.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * 角色权限关系
 *
 * @author liaoyanbo
 */
@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

}
