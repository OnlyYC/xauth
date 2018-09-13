package com.liaoyb.auth.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liaoyb.auth.modules.sys.domain.SysUserRole;
import com.liaoyb.auth.modules.sys.repository.SysUserRoleMapper;
import com.liaoyb.auth.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关系
 *
 * @author liaoyanbo
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
