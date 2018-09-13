package com.liaoyb.auth.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liaoyb.auth.modules.sys.domain.SysPermission;
import com.liaoyb.auth.modules.sys.repository.SysPermissionMapper;
import com.liaoyb.auth.modules.sys.service.SysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 *
 * @author liaoyanbo
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {


    @Override
    public List<SysPermission> getUserPermission(String userId) {
        return this.baseMapper.getUserPermission(userId);
    }
}
