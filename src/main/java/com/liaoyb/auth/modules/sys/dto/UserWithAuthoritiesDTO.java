package com.liaoyb.auth.modules.sys.dto;

import com.liaoyb.auth.modules.sys.domain.SysPermission;
import com.liaoyb.auth.modules.sys.domain.SysRole;
import com.liaoyb.auth.modules.sys.domain.SysUser;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserWithAuthoritiesDTO {
    private SysUser user;
    private List<SysPermission> permissionList;
    private List<SysRole> sysRoles;
    /**
     * 用户菜单
     */
    private List<SysPermission> menuList;
    /**
     * 权限
     */
    private Set<String> permissions;
}
