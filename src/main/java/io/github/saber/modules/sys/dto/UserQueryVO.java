package io.github.saber.modules.sys.dto;

import io.github.saber.modules.sys.domain.SysUser;
import lombok.Data;

import java.util.Date;

@Data
public class UserQueryVO {
    /**
     * 用户ID
     */
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
     * 创建者ID
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    public UserQueryVO() {
        // Empty constructor needed for Jackson.
    }

    public UserQueryVO(SysUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.email = user.getEmail();
        this.name = user.getName();
        this.status = user.getStatus();
        this.createUserId = user.getCreateUserId();
        this.createTime = user.getCreateTime();
    }
}
