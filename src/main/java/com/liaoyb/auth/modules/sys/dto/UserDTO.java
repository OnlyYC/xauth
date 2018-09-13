package com.liaoyb.auth.modules.sys.dto;

import com.liaoyb.auth.modules.sys.domain.SysUser;
import com.liaoyb.auth.modules.sys.domain.SysUserRole;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private String id;
    @NotBlank
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 人员名称/昵称
     */
    private String name;
    /**
     * 状态  0：正常   1：禁用
     */
    private Integer status;

    private Set<String> roles;

    public List<SysUserRole> toUserRole(String userId) {
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        //角色

        return roles.stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            return sysUserRole;
        }).collect(Collectors.toList());
    }

    public SysUser toUser() {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setUsername(username);
        sysUser.setName(name);
        sysUser.setEmail(email);
        sysUser.setMobile(mobile);
        sysUser.setStatus(status);
        return sysUser;
    }
}
