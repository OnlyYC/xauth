package io.github.saber.modules.sys.dto;

import io.github.saber.modules.sys.domain.SysPermission;
import io.github.saber.modules.sys.domain.SysRole;
import io.github.saber.modules.sys.domain.SysUser;
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
