package io.github.saber.modules.sys.controller;

import io.github.saber.modules.sys.dto.PasswordChangeDTO;
import io.github.saber.modules.sys.dto.UserVO;
import io.github.saber.modules.sys.service.SysUserService;
import io.github.saber.web.errors.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 当前登录账号管理
 *
 * @author liaoyanbo
 */
@Slf4j
@RestController
@RequestMapping("/api/sys")
public class AccountController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/account/me")
    public UserVO me() {
        log.debug("REST request to get current User");
        return sysUserService.getUserByLogin()
                .map(sysUserService::convert2UserVO)
                .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    /**
     * 修改密码
     *
     * @param passwordChangeDTO
     */
    @PostMapping("/account/change-password")
    public void changePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
        sysUserService.changePassword(passwordChangeDTO.getCurrentPassword(), passwordChangeDTO.getNewPassword());
    }
}
