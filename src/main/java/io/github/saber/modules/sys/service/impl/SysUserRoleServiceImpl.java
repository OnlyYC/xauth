package io.github.saber.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.github.saber.modules.sys.domain.SysUserRole;
import io.github.saber.modules.sys.repository.SysUserRoleMapper;
import io.github.saber.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关系
 *
 * @author liaoyanbo
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
