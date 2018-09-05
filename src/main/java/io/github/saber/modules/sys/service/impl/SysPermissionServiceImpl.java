package io.github.saber.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.github.saber.modules.sys.domain.SysPermission;
import io.github.saber.modules.sys.repository.SysPermissionMapper;
import io.github.saber.modules.sys.service.SysPermissionService;
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
