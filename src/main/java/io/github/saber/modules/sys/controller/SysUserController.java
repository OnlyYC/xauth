package io.github.saber.modules.sys.controller;

import com.baomidou.mybatisplus.plugins.Page;
import io.github.saber.common.beans.BatchDeleteForm;
import io.github.saber.modules.sys.domain.SysUser;
import io.github.saber.modules.sys.dto.UserDTO;
import io.github.saber.modules.sys.dto.UserQueryVO;
import io.github.saber.modules.sys.dto.UserVO;
import io.github.saber.modules.sys.form.UserPageForm;
import io.github.saber.modules.sys.service.SysUserService;
import io.github.saber.security.AuthoritiesConstants;
import io.github.saber.web.errors.BadRequestAlertException;
import io.github.saber.web.errors.UsernameAlreadyUsedException;
import io.github.saber.web.util.HeaderUtil;
import io.github.saber.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 用户管理
 *
 * @author liaoyanbo
 */
@Slf4j
@RestController
@RequestMapping("/api/sys")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;


    /**
     * 用户列表
     *
     * @param userPageForm
     * @return
     */
    @GetMapping("/users")
    public Page<UserQueryVO> list(@Valid UserPageForm userPageForm) {
        return sysUserService.getAllUsers(userPageForm.toPage(), userPageForm.getUsername());
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserVO> getUser(@PathVariable String userId) {
        log.debug("REST request to get User : {}", userId);
        return ResponseUtil.wrapOrNotFound(sysUserService.getUserById(userId).map(sysUserService::convert2UserVO));
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    @PutMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserVO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        if (StringUtils.isBlank(userDTO.getId())) {
            throw new BadRequestAlertException("update user must have an ID", "user", "id-not-exists");
        }
        Optional<SysUser> existingUser = sysUserService.getUserByLogin(userDTO.getUsername().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new UsernameAlreadyUsedException();
        }

        Optional<UserVO> updatedUser = sysUserService.updateUser(userDTO);
        return ResponseUtil.wrapOrNotFound(updatedUser);
    }

    /**
     * 创建用户
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public SysUser createUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to save User : {}", userDTO);
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "user", "id-exists");
        } else if (sysUserService.getUserByLogin(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new UsernameAlreadyUsedException();
        } else {
            SysUser sysUser = sysUserService.createUser(userDTO);
            //todo 发送激活邮件
            sysUser.setPassword("");
            return sysUser;
        }
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/users/{userId}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        log.debug("REST request to delete User: {}", userId);
        sysUserService.deleteUser(userId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("user.deleted", userId)).build();
    }

    /**
     * 批量删除用户
     *
     * @param batchDeleteForm
     * @return
     */
    @DeleteMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public void batchDeleteUser(@Valid @RequestBody BatchDeleteForm batchDeleteForm) {
        log.debug("REST request to batch delete User: {}", batchDeleteForm);
        sysUserService.batchDeleteUser(batchDeleteForm.getIds());
    }


}
