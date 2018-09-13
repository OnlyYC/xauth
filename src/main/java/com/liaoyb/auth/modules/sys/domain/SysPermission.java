package com.liaoyb.auth.modules.sys.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 权限ID
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 父菜单ID，一级菜单为null
     */
    private String parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;
    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;
}
