package com.liaoyb.auth.modules.sys.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 创建者ID
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;
}
