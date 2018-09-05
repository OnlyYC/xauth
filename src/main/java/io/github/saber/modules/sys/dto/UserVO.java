package io.github.saber.modules.sys.dto;

import io.github.saber.modules.sys.domain.SysPermission;
import io.github.saber.modules.sys.domain.SysRole;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserVO {

    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 人员名称/昵称
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：正常   1：禁用
     */
    private Integer status;

    /**
     * 用户菜单
     */
    private List<SysPermission> menuList;
    /**
     * 用户角色
     */
    private List<SysRole> roles;

    /**
     * 权限
     */
    private Set<String> permissions;
}
