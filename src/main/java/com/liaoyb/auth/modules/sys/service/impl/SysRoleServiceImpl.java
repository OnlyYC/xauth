package com.liaoyb.auth.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.liaoyb.auth.modules.sys.domain.SysRole;
import com.liaoyb.auth.modules.sys.repository.SysRoleMapper;
import com.liaoyb.auth.modules.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 *
 * @author liaoyanbo
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> getUserRole(String userId) {
        return this.baseMapper.getUserRole(userId);
    }

    @Override
    public List<SysRole> getRoles() {
        return this.baseMapper.selectByMap(Maps.newHashMap());
    }
}
