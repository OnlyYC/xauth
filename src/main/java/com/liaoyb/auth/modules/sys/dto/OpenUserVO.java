package com.liaoyb.auth.modules.sys.dto;

import com.liaoyb.auth.modules.sys.domain.SysUser;
import lombok.Data;

/**
 * 开发用户信息
 *
 * @author liaoyanbo
 */
@Data
public class OpenUserVO {
    private String openid;
    private String nickname;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;

    public static OpenUserVO of(SysUser sysUser) {
        OpenUserVO openUserVO = new OpenUserVO();
        openUserVO.setOpenid(sysUser.getId());
        openUserVO.setEmail(sysUser.getEmail());
        openUserVO.setMobile(sysUser.getMobile());
        openUserVO.setNickname(sysUser.getName());
        openUserVO.setUsername(sysUser.getUsername());
        return openUserVO;
    }
}
